package com.mycompany.creepyatlas.Audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import com.mycompany.creepyatlas.Utils.Distance;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DebugGraphics;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class AudioListener3D {
    private static long audioDeviceHandler;
    private static long audioContext;

    private static float positionX, positionY;

    public static final int SPACE_UNITS = 10;

    private static final List<AudioSource3D> allAudioSources = new ArrayList<>();

    public static void InitOpenAL() {
        audioDeviceHandler = alcOpenDevice((ByteBuffer) null);
        if (audioDeviceHandler == NULL) throw new IllegalStateException("Failed to open audio device.");
        ALCCapabilities caps = ALC.createCapabilities(audioDeviceHandler);
        audioContext = alcCreateContext(audioDeviceHandler, (int[]) null);
        alcMakeContextCurrent(audioContext);
        AL.createCapabilities(caps);

        alDistanceModel(AL_INVERSE_DISTANCE_CLAMPED);

        SetPosition(0, 0);
        alListener3f(AL_VELOCITY, 0, 0, 0);
        SetOrientation(0, 0, -1, 0, 1, 0);
    }

    public static void CleanupOpenAL() {
        alcDestroyContext(audioContext);
        alcCloseDevice(audioDeviceHandler);
    }

    public static void SetPosition(float positionX, float positionY) {
        AudioListener3D.positionX = positionX * SPACE_UNITS;
        AudioListener3D.positionY = positionY * SPACE_UNITS;
        alListener3f(AL_POSITION, AudioListener3D.positionX, AudioListener3D.positionY, 0);

        UpdateSourcesGain();
    }

    public static void SetOrientation(
            float atX, float atY, float atZ,
            float upX, float upY, float upZ) 
    {
        float[] orientation = {atX, atY, atZ, upX, upY, upZ};
        alListenerfv(AL_ORIENTATION, orientation);
    }

    public static float GetPositionX() { return positionX; }
    public static float GetPositionY() { return positionY; }

    public static void RegisterSource(AudioSource3D source) {
        allAudioSources.add(source);
        UpdateSourcesGain();
    }

    public static void UpdateSourcesGain() {
        for (AudioSource3D source : allAudioSources) {
            if (Distance.Euclidean(source.getX(), source.getY(), positionX, positionY) > 2.1f * SPACE_UNITS) {
                source.setGain(0f);
            } else {
                source.setGain(1f);
            }
        }
    }

    public static void DisabelAllAudioSources(){
        for (AudioSource3D source : allAudioSources) {
            if (source != null)
            {
                source.Disable();
            }
        }
    }

    public static void EnableAllAudioSources(){
        for (AudioSource3D source : allAudioSources) {
            if (source != null)
            {
                source.Enable();
            }
        }
        UpdateSourcesGain();
    }
}
