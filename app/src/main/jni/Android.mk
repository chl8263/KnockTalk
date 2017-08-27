LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := VideoPlayer
LOCAL_SRC_FILES := interface.c
LOCAL_LDLIBS += -llog -lz -landroid
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include
LOCAL_SHARED_LIBRARIES := avcodec avformat avutil swresample swscale

include $(BUILD_SHARED_LIBRARY)

$(call import-module, ffmpeg-3.2.7/android/arm)