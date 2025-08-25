package com.mycompany.creepyatlas;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioListener3D {
    private static long device;
    private static long context;

    private static float x, y, z;

    public static void initOpenAL() {
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) throw new IllegalStateException("Failed to open audio device.");
        ALCCapabilities caps = ALC.createCapabilities(device);
        context = alcCreateContext(device, (int[]) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(caps);

        // Posición inicial y orientación default
        setPosition(0, 0, 0);
        setVelocity(0, 0, 0);
        setOrientation(0, 0, -1, 0, 1, 0);
    }

    public static void cleanupOpenAL() {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    public static void setPosition(float x, float y, float z) {
        AudioListener3D.x = x;
        AudioListener3D.y = y;
        AudioListener3D.z = z;
        alListener3f(AL_POSITION, x, y, z);
    }

    public static void setVelocity(float vx, float vy, float vz) {
        alListener3f(AL_VELOCITY, vx, vy, vz);
    }

    public static void setOrientation(
            float atX, float atY, float atZ,
            float upX, float upY, float upZ) {
        float[] ori = {atX, atY, atZ, upX, upY, upZ};
        alListenerfv(AL_ORIENTATION, ori);
    }

    // Getters
    public static float getX() { return x; }
    public static float getY() { return y; }
    public static float getZ() { return z; }
}
