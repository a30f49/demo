#!/bin/bash
#ndk-build NDK_LOG=1
#ndk-build NDK_PROJECT_PATH=.
#ndk-build APP_BUILD_SCRIPT=./Android.mk
#ndk-build NDK_APP_APPLICATION_MK=./Application.mk
#ndk-build clean
#ndk-build -B //rebuild force
#ndk-build NDK_DEBUG=1 //debug build
#ndk-build NDK_DEBUG=0 //release build
#ndk-build NDK_OUT=./path/to
#ndk-build -C /path/to

/opt/android-ndk-r10d/ndk-build
