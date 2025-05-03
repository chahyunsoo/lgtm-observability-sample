package spring.lgtm_observability_sample

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "coupons")
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    val code: String = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase(),
    
    val discountAmount: Int,
    
    val discountType: DiscountType,
    
    val minimumOrderAmount: Int = 0,
    
    val maxDiscountAmount: Int? = null,
    
    val issuedTo: String? = null,
    
    val isUsed: Boolean = false,
    
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    val expiresAt: LocalDateTime
)
