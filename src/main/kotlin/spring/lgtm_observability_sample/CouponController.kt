package spring.lgtm_observability_sample

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/coupons")
class CouponController(
    private val couponService: CouponService
) {
    private val logger = LoggerFactory.getLogger(CouponController::class.java)

    @PostMapping
    fun issueCoupon(
        @Valid @RequestBody request: CouponIssueRequest
    ): ResponseEntity<CouponResponse> {
        logger.info("쿠폰 발급 API 호출: {}", request)
        val couponResponse = couponService.issueCoupon(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(couponResponse)
    }
    
    @GetMapping("/{code}")
    fun getCoupon(
        @PathVariable code: String
    ): ResponseEntity<CouponResponse> {
        logger.info("쿠폰 조회 API 호출: {}", code)
        val couponResponse = couponService.getCouponByCode(code)
        return ResponseEntity.ok(couponResponse)
    }
    
    @GetMapping("/user/{userId}")
    fun getUserCoupons(
        @PathVariable userId: String
    ): ResponseEntity<List<CouponResponse>> {
        logger.info("사용자 쿠폰 조회 API 호출: {}", userId)
        val coupons = couponService.getUserCoupons(userId)
        return ResponseEntity.ok(coupons)
    }
}