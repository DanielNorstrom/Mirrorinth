package mirrorinth;

/**
 * Class to hold "global" constants. Likely to be moved elsewhere.
 */
public final class Config
{
    static final int SCALE = 2;
    static final int TILESIZE = 20 * SCALE;
    static final int RESX = 1080;
    static final int RESY = 720;
    static final int SPRITESIZE = 16 * SCALE;
    static final int ANIMDELAY = 10;
    static final int MOBDELAY = ANIMDELAY * TILESIZE + ANIMDELAY * 10;


    private Config() {}
}
