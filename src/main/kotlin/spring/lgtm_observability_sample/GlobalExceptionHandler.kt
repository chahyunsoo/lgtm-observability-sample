package spring.lgtm_observability_sample

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    
    @ExceptionHandler(CouponNotFoundException::class)
    fun handleCouponNotFoundException(ex: CouponNotFoundException): ResponseEntity<ErrorResponse> {
        logger.error("쿠폰을 찾을 수 없음: {}", ex.message)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse("COUPON_NOT_FOUND", ex.message ?: "쿠폰을 찾을 수 없습니다"))
    }
    
    @ExceptionHandler(CouponAlreadyExistsException::class)
    fun handleCouponAlreadyExistsException(ex: CouponAlreadyExistsException): ResponseEntity<ErrorResponse> {
        logger.error("쿠폰이 이미 존재함: {}", ex.message)
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorResponse("COUPON_ALREADY_EXISTS", ex.message ?: "쿠폰이 이미 존재합니다"))
    }
    
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.allErrors.joinToString(", ") { error ->
            val fieldName = (error as? FieldError)?.field ?: "unknown"
            "$fieldName: ${error.defaultMessage}"
        }
        logger.error("유효성 검사 실패: {}", errors)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse("VALIDATION_ERROR", errors))
    }
    
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error("예상치 못한 오류 발생: {}", ex.message, ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다"))
    }
}