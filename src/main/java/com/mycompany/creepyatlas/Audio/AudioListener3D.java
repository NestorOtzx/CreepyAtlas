package com.mycompany.creepyatlas.Audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioListener3D {
    private static long device;
    private static long context;

    private static float x, y, z;

    public static final int SPACE_UNITS = 10;

    // --- lista de fuentes registradas ---
    private static final List<AudioSource3D> sources = new ArrayList<>();

    public static void initOpenAL() {
        device = alcOpenDevice((ByteBuffer) null);
        if (device == NULL) throw new IllegalStateException("Failed to open audio device.");
        ALCCapabilities caps = ALC.createCapabilities(device);
        context = alcCreateContext(device, (int[]) null);
        alcMakeContextCurrent(context);
        AL.createCapabilities(caps);

        alDistanceModel(AL_INVERSE_DISTANCE_CLAMPED);

        setPosition(0, 0);
        setVelocity(0, 0, 0);
        setOrientation(0, 0, -1, 0, 1, 0);
    }

    public static void cleanupOpenAL() {
        alcDestroyContext(context);
        alcCloseDevice(device);
    }

    public static void setPosition(float x, float y) {
        AudioListener3D.x = x * SPACE_UNITS;
        AudioListener3D.y = y * SPACE_UNITS;
        AudioListener3D.z = 0;
        alListener3f(AL_POSITION, AudioListener3D.x, AudioListener3D.y, AudioListener3D.z);

        updateSourcesGain();
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

    public static float getX() { return x; }
    public static float getY() { return y; }
    public static float getZ() { return z; }

    // --- registrar fuente ---
    public static void registerSource(AudioSource3D source) {
        sources.add(source);
    }

    // --- recalcular volúmenes ---
    private static void updateSourcesGain() {
        for (AudioSource3D source : sources) {
            float dx = source.getX() - x;
            float dy = source.getY() - y;
            float dz = source.getZ() - z;
            float dist = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);

            if (dist > 1.1f * SPACE_UNITS) {
                source.setGain(0f); // mutea
            } else {
                source.setGain(1f); // volumen normal
            }
        }
    }
}
