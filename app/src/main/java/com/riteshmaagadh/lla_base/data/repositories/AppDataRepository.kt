package com.riteshmaagadh.lla_base.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.riteshmaagadh.lla_base.data.entities.QuickLearn
import com.riteshmaagadh.lla_base.data.modals.Lesson
import com.riteshmaagadh.lla_base.data.modals.QlPhrase
import com.riteshmaagadh.lla_base.data.modals.Sublesson
import com.riteshmaagadh.lla_base.data.modals.Topic
import com.riteshmaagadh.lla_base.utils.FirebaseConstants
import com.riteshmaagadh.lla_base.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AppDataRepository {

    companion object {
        private const val TAG = "LessonsRepository"
    }

    private val firebaseFirestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }


    suspend fun getLessons() = suspendCoroutine<NetworkState<List<Lesson>>> { continuation ->

        val snapshot = firebaseFirestore.collection(FirebaseConstants.LESSONS_COLLECTION)
            .orderBy(FirebaseConstants.INDEX, Query.Direction.ASCENDING)
            .get()

        snapshot
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val lessons: MutableList<Lesson> = mutableListOf()

                    it.documents.forEachIndexed { index, queryDocumentSnapshot ->
                        val lesson = queryDocumentSnapshot.toObject(Lesson::class.java)
                        val sublessonsList: MutableList<Lesson.SubLesson> = mutableListOf()
                        val sublessonsQS =
                            queryDocumentSnapshot["sublessons"]
                        if (sublessonsQS != null) {
                            val sublessons: List<HashMap<String, Any>> =
                                sublessonsQS as List<HashMap<String, Any>>
                            sublessons.forEachIndexed { index, hashMap ->
                                sublessonsList.add(
                                    Lesson.SubLesson(
                                        hashMap["index"] as Long,
                                        hashMap["sublesson_id"] as String
                                    )
                                )
                            }
                            lesson?.putSubLessons(sublessonsList)
                        }
                        if (lesson != null) {
                            lessons.add(lesson)
                        }
                    }
                    continuation.resume(NetworkState.success(lessons))
                }
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    suspend fun getTopics(subLessonId: String) =
        suspendCoroutine<NetworkState<List<Topic>>> { continuation ->

            val snapshot = firebaseFirestore.collection(FirebaseConstants.SUBLESSON_COLLECTION)
                .document(subLessonId)
                .get()

            snapshot
                .addOnSuccessListener {
                    val sublesson = it.toObject(Sublesson::class.java)
                    continuation.resume(NetworkState.success(sublesson?.topics!!))
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }


    suspend fun getQuickLearnData() = flow<NetworkState<List<QuickLearn>>> {
        emit(NetworkState.loading())
        try {
            val snapshot = firebaseFirestore.collection(FirebaseConstants.QUICK_LEARN)
                .orderBy(FirebaseConstants.INDEX, Query.Direction.ASCENDING)
                .get()
                .await()

            emit(NetworkState.success(snapshot.toObjects(QuickLearn::class.java)))
        } catch (e: Exception) {
            emit(NetworkState.failed(e.message!!))
        }
    }.flowOn(Dispatchers.IO)



    suspend fun getQlPhrases(docId: String) = flow<NetworkState<List<QlPhrase>>> {
        emit(NetworkState.loading())
        try {
            val snapshot = firebaseFirestore.collection(FirebaseConstants.QUICK_LEARN)
                .document(docId)
                .collection(FirebaseConstants.QL_PHRASES)
                .orderBy(FirebaseConstants.INDEX, Query.Direction.ASCENDING)
                .get()
                .await()

            emit(NetworkState.success(snapshot.toObjects(QlPhrase::class.java)))
        } catch (e: Exception) {
            emit(NetworkState.failed(e.message!!))
        }
    }.flowOn(Dispatchers.IO)



//    suspend fun getLessons() = flow<NetworkState<List<Lesson>>> {
//        emit(NetworkState.loading())
//        try {
//            val snapshot = firebaseFirestore.collection(FirebaseConstants.LESSONS_COLLECTION)
//                .orderBy(FirebaseConstants.INDEX, Query.Direction.ASCENDING)
//                .get()
//                .await()
//
//            val lessons: MutableList<Lesson> = mutableListOf()
//
//            for (document in snapshot) {
//                val lesson = document.toObject(Lesson::class.java)
//                val sublessons = document.get("sublessons")
//                if (sublessons != null) {
//                    lesson.putSubLessons(sublessons as List<Lesson.SubLesson>)
//                }
//                lessons.add(lesson)
//            }
//
//            emit(NetworkState.success(lessons))
//        } catch (e: Exception) {
//            emit(NetworkState.failed(e.message!!))
//        }
//    }.flowOn(Dispatchers.IO)


}