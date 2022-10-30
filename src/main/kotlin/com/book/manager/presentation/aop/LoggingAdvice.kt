package com.book.manager.presentation.aop

import com.book.manager.application.service.security.BookManagerUserDetails
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

private val logger = LoggerFactory.getLogger(LoggingAdvice::class.java)

@Aspect
@Component
class LoggingAdvice {
  @Before("execution(* com.book.manager.presentation.controller..*.*(..))")
  fun beforeLog(joinPoint: JoinPoint) {
    val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
    logger.info("Start: ${joinPoint.signature} userId=${user.id}")
    logger.info("Class: ${joinPoint.target.javaClass}")
    logger.info("Session: ${(RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session.id}")
  }

  @After("execution(* com.book.manager.presentation.controller..*.*(..))")
  fun afterLog(joinPoint: JoinPoint) {
    val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
    logger.info("End: ${joinPoint.signature} userId=${user.id}")
  }

  @Around("execution(* com.book.manager.presentation.controller..*.*(..))")
  fun aroundList(joinPoint: ProceedingJoinPoint): Any? {
    val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
    logger.info("String Proceed: ${joinPoint.signature} userId=${user.id}")

    val result = joinPoint.proceed()
    logger.info("End Proceed: ${joinPoint.signature} userId=${user.id}")

    return result
  }
}