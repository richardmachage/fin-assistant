package com.transsion.financialassistant.data.cache

import android.util.LruCache

object AppCache {
    private const val MAX_CACHE_SIZE = 50
    private val cache: LruCache<String, Any> = LruCache<String, Any>(MAX_CACHE_SIZE)

    //add item to cache
    fun <T> put(key: String, value: T) {
        synchronized(cache) {
            cache.put(key, value)
        }
    }

    //remove item from cache
    fun remove(key: String) {
        synchronized(cache) {
            cache.remove(key)
        }
    }

    //get item from cache
    fun <T> get(key: String): T? {
        synchronized(cache) {
            return cache.get(key) as? T
        }
    }

    //check if key exists in cache
    fun containsKey(key: String): Boolean {
        synchronized(cache) {
            return cache.get(key) != null
        }
    }

    // Get current cache size
    fun size(): Int {
        synchronized(cache) {
            return cache.size()
        }
    }


    //Clear all cached items
    fun clear() {
        synchronized(cache) {
            cache.evictAll()
        }
    }

}