package com.gdi.conferenceplanner.util

import javax.inject.Qualifier
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class SevenDaysNotification
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TwoDaysNotification

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MessageNotification
