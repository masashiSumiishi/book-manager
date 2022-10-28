/*
 * Auto-generated file. Created by MyBatis Generator
 */
package com.book.manager.infrastructure.database.record

import java.util.Date

data class RentalRecord(
    var bookId: Long? = null,
    var userId: Long? = null,
    var rentalDatetime: Date? = null,
    var returnDatetime: Date? = null
)