/*
 * created by alex5402 at 8.42 pm 11 th March 2023
 * you can contact me on telegram username @alex5402*/
#include "jni.h"
#include "KittyMemory/MemoryPatch.h"
#include <pthread.h>

// this thread will gonna turn on automatically
void *hack_thread(void *) {
    ProcMap il2cppMap;
    do {
        il2cppMap = KittyMemory::getLibraryMap("libName");
        sleep(1);
    } while (!il2cppMap.isValid());

                              // libname       // offset   // hex value
    MemoryPatch::createWithHex("libexample.so", 0x123456, "00 00 01 01").Modify();
    return nullptr;
}

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *globalEnv;
    vm->GetEnv((void **) &globalEnv, JNI_VERSION_1_6);

    // Create a new thread so it does not block the main thread, means the game would not freeze
    pthread_t ptid;
    pthread_create(&ptid, nullptr, hack_thread, nullptr);

    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL
JNI_OnUnload(JavaVM *vm, void *reserved) {}