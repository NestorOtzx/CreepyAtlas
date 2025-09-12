package com.mycompany.creepyatlas.Audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import com.mycompany.creepyatlas.Utils.Distance;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioListener3D {
    private static long audioDeviceHandler;
    private static long audioContext;

    private static float positionX, positionY;

    public static final int SPACE_UNITS = 10;

    private static final List<AudioSource3D> allAudioSources = new ArrayList<>();

    public static void initOpenAL() {
        audioDeviceHandler = alcOpenDevice((ByteBuffer) null);
        if (audioDeviceHandler == NULL) throw new IllegalStateException("Failed to open audio device.");
        ALCCapabilities caps = ALC.createCapabilities(audioDeviceHandler);
        audioContext = alcCreateContext(audioDeviceHandler, (int[]) null);
        alcMakeContextCurrent(audioContext);
        AL.createCapabilities(caps);

        alDistanceModel(AL_INVERSE_DISTANCE_CLAMPED);

        setPosition(0, 0);
        alListener3f(AL_VELOCITY, 0, 0, 0);
        setOrientation(0, 0, -1, 0, 1, 0);
    }

    public static void cleanupOpenAL() {
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDeviceHandler);
    }

    public static void setPosition(float positionX, float positionY) {
        AudioListener3D.positionX = positionX * SPACE_UNITS;
        AudioListener3D.positionY = positionY * SPACE_UNITS;
        alListener3f(AL_POSITION, AudioListener3D.positionX, AudioListener3D.positionY, 0);

        updateSourcesGain();
    }

    public static void setOrientation(
            float atX, float atY, float atZ,
            float upX, float upY, float upZ) 
    {
        float[] orientation = {atX, atY, atZ, upX, upY, upZ};
        alListenerfv(AL_ORIENTATION, orientation);
    }

    public static float getPositionX() { return positionX; }
    public static float getPositionY() { return positionY; }

    public static void registerSource(AudioSource3D source) {
        allAudioSources.add(source);
        updateSourcesGain();
    }

    public static void updateSourcesGain() {
        for (AudioSource3D source : allAudioSources) {
            if (Distance.Euclidean(source.getPositionX(), source.getPositionY(), positionX, positionY) > 2.1f * SPACE_UNITS) {
                source.setGain(0f);
            } else {
                source.setGain(1f);
            }
        }
    }

    public static void disabelAllAudioSources(){
        for (AudioSource3D source : allAudioSources) {
            if (source != null)
            {
                source.disable();
            }
        }
    }

    public static void enableAllAudioSources(){
        for (AudioSource3D source : allAudioSources) {
            if (source != null)
            {
                source.enable();
            }
        }
        updateSourcesGain();
    }
}
