/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhaojian.jolly.constant;

public final class Constants {

    public static final float IMAGE_CACHE_HEAP_PERCENTAGE = 1f / 6f;

    public static final int DISPLAY_PHOTO_SIZE = 640;

    public static final long SCALE_ANIMATION_DURATION_FULL_DISTANCE = 800;

    public static final String FACEBOOK_APP_ID = "134669876670695";

    public static final String[] FACEBOOK_PERMISSIONS = {"publish_stream", "user_photos",
            "manage_pages",
            "user_groups", "user_events"};

    public static final int FACE_DETECTOR_MAX_FACES = 8;

    public static final String CRITTERCISM_API_KEY = "50994cc4d5f9b95cfb000005";

    public static final String INTENT_SERVICE_UPLOAD_ALL = "photup.intent.action.UPLOAD_ALL";
    public static final String INTENT_PHOTO_TAKEN = "photup.intent.action.PHOTO_TAKEN";
    public static final String INTENT_NEW_PERMISSIONS = "photup.intent.action.NEW_PERMISSIONS";
    public static final String INTENT_LOGOUT = "photup.intent.action.LOGOUT";

    public static final String PROMO_POST_URL
            = "https://play.google.com/store/apps/details?id=uk.co.senab.photup";
    public static final String PROMO_IMAGE_URL
            = "http://www.senab.co.uk/wp-content/uploads/2012/08/photup_logo.png";
}
