package com.jeno.gitrepoproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jeno.gitrepoproject.jdos.RepoListData

@Database(entities = [RepoListData::class],version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract val repoDao : RepoDao

    companion object{
        private var instance : RepoDatabase?= null
        fun getInstance(context: Context): RepoDatabase {
            synchronized(this) {
                var instance = instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepoDatabase::class.java,
                        "git_repo_database"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }
    }


}