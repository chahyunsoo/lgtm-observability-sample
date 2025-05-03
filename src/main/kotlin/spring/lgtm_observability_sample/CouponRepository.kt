package spring.lgtm_observability_sample

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponRepository : JpaRepository<Coupon, Long> {
    fun findByCode(code: String): Optional<Coupon>
    fun findByIssuedTo(userId: String): List<Coupon>
    fun existsByCode(code: String): Boolean
}