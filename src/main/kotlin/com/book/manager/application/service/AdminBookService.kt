package com.book.manager.application.service

import com.book.manager.domain.model.Book
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class AdminBookService(
  private val bookRepository: BookRepository
) {
  @Transactional
  fun register(book: Book) {
    bookRepository.findWithRental(book.id)?.let { throw IllegalAccessException("既に存在する書籍ID:${book.id}") }
    bookRepository.register(book)
  }

  @Transactional
  fun update(bookId: Long, title: String?, author: String?, releaseDate: LocalDate?) {
    bookRepository.findWithRental(bookId) ?: throw IllegalAccessException("存在しない書籍ID:$bookId")
  }

  @Transactional
  fun delete(bookId: Long) {
    bookRepository.findWithRental(bookId) ?: throw IllegalAccessException("存在しない書籍ID:$bookId")
    bookRepository.delete(bookId)
  }
}