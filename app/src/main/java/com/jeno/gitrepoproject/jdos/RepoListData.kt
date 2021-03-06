package com.jeno.gitrepoproject.jdos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class RepoListData(
    @ColumnInfo(name = "username")
    val username: String,
    @PrimaryKey
    @ColumnInfo(name = "repositoryName")
    val repositoryName: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "language")
    var language: String?,
    @ColumnInfo(name = "languageColor")
    val languageColor: String?,
    @ColumnInfo(name = "totalStars")
    val totalStars: Int
)

