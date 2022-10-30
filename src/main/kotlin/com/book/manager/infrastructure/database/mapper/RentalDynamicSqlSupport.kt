/*
 * Auto-generated file. Created by MyBatis Generator
 */
package com.book.manager.infrastructure.database.mapper

import java.sql.JDBCType
import java.util.Date
import org.mybatis.dynamic.sql.SqlTable
import java.time.LocalDateTime

object RentalDynamicSqlSupport {
    object Rental : SqlTable("rental") {
        val bookId = column<Long>("book_id", JDBCType.BIGINT)

        val userId = column<Long>("user_id", JDBCType.BIGINT)

        val rentalDatetime = column<LocalDateTime>("rental_datetime", JDBCType.TIMESTAMP)

        val returnDeadline = column<LocalDateTime>("return_deadline", JDBCType.TIMESTAMP)
    }
}