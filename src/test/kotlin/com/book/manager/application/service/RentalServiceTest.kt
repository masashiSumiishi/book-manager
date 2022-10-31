package com.book.manager.application.service

import com.book.manager.domain.enum.RoleType
import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.model.Rental
import com.book.manager.domain.model.User
import com.book.manager.domain.repository.BookRepository
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.domain.repository.UserRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class RentalServiceTest {
  private val userRepository = mock<UserRepository>()
  private val bookRepository = mock<BookRepository>()
  private val rentalRepository = mock<RentalRepository>()

  private val rentalService = RentalService(userRepository, bookRepository, rentalRepository)

  @Test
  fun `endRental when book is rental then delete to rental`() {
    val userId = 100L
    val bookId = 1000L

    val user = User(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
    val book = Book(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
    val rental = Rental(bookId, userId, LocalDateTime.now(), LocalDateTime.MAX)
    val bookWithRental = BookWithRental(book, rental)

    whenever(userRepository.find(any() as Long)).thenReturn(user)
    whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

    rentalService.endRental(bookId, userId)

    verify(userRepository).find(userId)
    verify(bookRepository).findWithRental(bookId)
    verify(rentalRepository).endRental(bookId)
  }
}