package net.cny.core.audio;

import java.io.IOException;
import java.nio.IntBuffer;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import net.cny.core.util.SoundUtils;

public class Audio
{

    private IntBuffer buffer;
    private int source;
    private long time;

    public Audio(String soundPath)
    {
        Initialize(soundPath);
    }

    public void Initialize(String soundPath)
    {
        buffer = BufferUtils.createIntBuffer(1);
        AL10.alGenBuffers(buffer);

        try
        {
            SoundUtils.createBufferData(buffer.get(0), soundPath);
            time = (buffer.get(0) + 1);
        }
        catch (UnsupportedAudioFileException | IOException e)
        {
            e.printStackTrace();
        }

        source = AL10.alGenSources();
        AL10.alSourcei(source, AL10.AL_BUFFER, buffer.get(0));
        AL10.alSource3f(source, AL10.AL_POSITION, 0, 0, 0f);
        AL10.alSource3f(source, AL10.AL_VELOCITY, 0f, 0f, 0f);

        //fun stuff
        AL10.alSourcef(source, AL10.AL_PITCH, 1f);
        AL10.alSourcef(source, AL10.AL_GAIN, 1f);
        AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_FALSE);

    }

    public void Play()
    {
        AL10.alSourcePlay(source);
    }

    public void Pause()
    {
        AL10.alSourcePause(source);
    }

    public void Stop()
    {
        AL10.alSourceStop(source);
    }

    public void CleanUp()
    {
        Stop();
        AL10.alDeleteBuffers(buffer);
        AL10.alDeleteSources(source);
    }

    public void SetPosition(float x, float y)
    {
        AL10.alSource3f(source, AL10.AL_POSITION, x, y, 0f);
    }

    public void SetLoop(boolean a)
    {
        AL10.alSourcei(source, AL10.AL_LOOPING, a ? AL10.AL_TRUE : AL10.AL_FALSE);
    }

    public void SetPitch(float p)
    {
        AL10.alSourcef(source, AL10.AL_PITCH, p);
    }
    
    public void SetGain(float gain)
    {
        AL10.alSourcef(source, AL10.AL_GAIN, gain);
    }

    public long GetTime()
    {
        return time;
    }
}
