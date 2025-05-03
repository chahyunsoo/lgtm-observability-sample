package spring.lgtm_observability_sample

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CouponIssueRequest(
    val code: String? = null,
    
    @field:NotNull(message = "할인 금액은 필수입니다")
    @field:Min(1, message = "할인 금액은 1 이상이어야 합니다")
    val discountAmount: Int,
    
    @field:NotNull(message = "할인 유형은 필수입니다")
    val discountType: DiscountType,
    
    val minimumOrderAmount: Int? = null,
    
    val maxDiscountAmount: Int? = null,
    
    val userId: String? = null,
    
    val expiresAt: LocalDateTime? = null
)