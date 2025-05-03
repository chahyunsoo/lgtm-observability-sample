package spring.lgtm_observability_sample

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class CouponService(private val couponRepository: CouponRepository) {
    
    private val logger = LoggerFactory.getLogger(CouponService::class.java)

    @Transactional
    fun issueCoupon(request: CouponIssueRequest): CouponResponse {
        logger.info("쿠폰 발급 요청: {}", request)
        
        // 커스텀 코드인 경우 중복 체크
        if (request.code != null && couponRepository.existsByCode(request.code)) {
            logger.error("중복된 쿠폰 코드: {}", request.code)
            throw CouponAlreadyExistsException("이미 존재하는 쿠폰 코드입니다: ${request.code}")
        }
        
        val coupon = Coupon(
            code = request.code ?: generateRandomCode(),
            discountAmount = request.discountAmount,
            discountType = request.discountType,
            minimumOrderAmount = request.minimumOrderAmount ?: 0,
            maxDiscountAmount = if (request.discountType == DiscountType.PERCENTAGE) request.maxDiscountAmount else null,
            issuedTo = request.userId,
            expiresAt = request.expiresAt ?: LocalDateTime.now().plusDays(30)
        )
        
        val savedCoupon = couponRepository.save(coupon)
        logger.info("쿠폰이 발급되었습니다: {}", savedCoupon.code)
        
        return CouponResponse.from(savedCoupon)
    }
    
    @Transactional(readOnly = true)
    fun getCouponByCode(code: String): CouponResponse {
        logger.info("쿠폰 코드로 조회: {}", code)
        val coupon = couponRepository.findByCode(code)
            .orElseThrow { 
                logger.error("쿠폰을 찾을 수 없음: {}", code)
                CouponNotFoundException("쿠폰을 찾을 수 없습니다: $code") 
            }
            
        return CouponResponse.from(coupon)
    }
    
    @Transactional(readOnly = true)
    fun getUserCoupons(userId: String): List<CouponResponse> {
        logger.info("사용자 쿠폰 조회: {}", userId)
        return couponRepository.findByIssuedTo(userId)
            .map { CouponResponse.from(it) }
    }
    
    private fun generateRandomCode(): String {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase()
    }
}