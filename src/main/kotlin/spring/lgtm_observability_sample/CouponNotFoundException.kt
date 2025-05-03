package spring.lgtm_observability_sample

class CouponNotFoundException(message: String) : RuntimeException(message)

class CouponAlreadyExistsException(message: String) : RuntimeException(message)