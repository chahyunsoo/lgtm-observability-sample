package spring.lgtm_observability_sample

import java.time.LocalDateTime

data class CouponResponse(
    val id: Long?,
    val code: String,
    val discountAmount: Int,
    val discountType: DiscountType,
    val minimumOrderAmount: Int,
    val maxDiscountAmount: Int?,
    val issuedTo: String?,
    val isUsed: Boolean,
    val createdAt: LocalDateTime,
    val expiresAt: LocalDateTime
) {
    companion object {
        fun from(coupon: Coupon): CouponResponse {
            return CouponResponse(
                id = coupon.id,
                code = coupon.code,
                discountAmount = coupon.discountAmount,
                discountType = coupon.discountType,
                minimumOrderAmount = coupon.minimumOrderAmount,
                maxDiscountAmount = coupon.maxDiscountAmount,
                issuedTo = coupon.issuedTo,
                isUsed = coupon.isUsed,
                createdAt = coupon.createdAt,
                expiresAt = coupon.expiresAt
            )
        }
    }
}