package de.sebastian_gnich.blubberblas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;


/**
 * Created by GnichS on 15.12.2015.
 */
public class BlubberView extends TextureView implements TextureView.SurfaceTextureListener {

    private static final String TAG = "Zewa";

    private int mainColor = Color.WHITE;
    private float speed = 100f;
    private int count = 10;

    private int maxSize = 120;
    private int minSize = 20;
    private int maxSpeed = 30;
    private int minSpeed = 1;

    private float viewHeight, viewWidth;

    private Paint backgroundPaint = new Paint();
    private Paint circlePaint = new Paint();

    //private ArrayList<Blubber> blubberList = new ArrayList<>();

    private Blubber[] blubbArray;

    private static final int MAX_FPS = 60;
    private static final int FRAME_PERIOD = 1000/MAX_FPS;

    int STATE_CURRENT;
    static final int STATE_INITIALIZE = -1;
    static final int STATE_PAUSE = 2;

    mThread thread;

    public BlubberView(Context context) {
        super(context);
        init();
    }

    public BlubberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlubberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void drawBackground(Canvas canvas)
    {
        canvas.drawRect(0f, 0f, viewWidth, viewHeight, backgroundPaint);
        //Log.d("Zewa", "Background drawn: "+viewWidth+"-"+viewHeight);
    }

    private void drawCircles(Canvas canvas)
    {
        for (int i = 0; i < count; i++)
        {
            // Wenn "neu"
            if (blubbArray[i] != null && blubbArray[i].getPositionY() == -999)
            {
                Paint thisPaint = new Paint(blubbArray[i].getPaint());
                thisPaint.setAlpha(Helper.randomNumber(20, 200));

                blubbArray[i].setPaint(thisPaint);
                blubbArray[i].setRadius(Helper.randomNumber(maxSize, minSize));
                blubbArray[i].setPositionY(viewHeight + blubbArray[i].getRadius());
                blubbArray[i].setPositionX(Helper.randomNumber(Math.round(0 - blubbArray[i].getRadius()), Math.round(viewWidth + blubbArray[i].getRadius())));
                blubbArray[i].setSpeed(Helper.randomNumber(maxSpeed, minSpeed));
            }
            // Wenn nach oben gefahren
            else if (blubbArray[i] != null && blubbArray[i].getPositionY() < 0 - (blubbArray[i].getRadius()) )
            {
                blubbArray[i].setPositionY(-999);
            }
            // ist irgendwo in der Mitte
            else if (blubbArray[i] != null)
            {
                blubbArray[i].setPositionY(blubbArray[i].getPositionY() - blubbArray[i].getSpeed());
            }

            //Log.d("Zewa", "thisPos: "+blubb.getPositionY());
            if (blubbArray[i] != null)
            {
                canvas.drawCircle( blubbArray[i].getPositionX(), blubbArray[i].getPositionY(), blubbArray[i].getRadius(), blubbArray[i].getPaint() );
            }
        }
        /*
        for (Blubber blubb : blubberList)
        {
            // Wenn "neu"
            if (blubb.getPositionY() == -999)
            {
                Paint thisPaint = new Paint(blubb.getPaint());
                thisPaint.setAlpha(Helper.randomNumber(20, 200));

                blubb.setPaint( thisPaint );
                blubb.setRadius(Helper.randomNumber(maxSize, minSize));
                blubb.setPositionY(viewHeight + blubb.getRadius());
                blubb.setPositionX(Helper.randomNumber(Math.round(0 - blubb.getRadius()), Math.round(viewWidth + blubb.getRadius())));
                blubb.setSpeed(Helper.randomNumber(maxSpeed, minSpeed));
            }
            // Wenn nach oben gefahren
            else if (blubb.getPositionY() < 0 - (blubb.getRadius()) )
            {
                blubb.setPositionY(-999);
            }
            // ist irgendwo in der Mitte
            else
            {
                blubb.setPositionY(blubb.getPositionY() - blubb.getSpeed());
            }

            //Log.d("Zewa", "thisPos: "+blubb.getPositionY());

            canvas.drawCircle( blubb.getPositionX(), blubb.getPositionY(), blubb.getRadius(), blubb.getPaint() );
        }
        */
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();
    }

    private void init()
    {
        setSurfaceTextureListener(this);
        setOpaque(false);

        blubbArray = new Blubber[count];

        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(Color.parseColor("#1A237E"));

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(mainColor);

        createCircles();
    }

    private void createCircles()
    {
        for (int i = 0; i < count; i++)
        {
            blubbArray[i] = getRandomBlubber();
            //blubberList.add( getRandomBlubber() );
        }
        if (thread != null)
        {
            thread.setRunning(true);
        }
    }


    public void setCount(int count) {
        this.count = count;

        if (thread != null)
        {
            thread.setRunning(false);
        }

        if (count > 0 && count <= 400)
        {
            //blubberList = new ArrayList<>();
            blubbArray = new Blubber[count];
            createCircles();
        }
    }

    public int getCount() {
        return count;
    }

    private Blubber getRandomBlubber()
    {
        Paint thisPaint = new Paint(circlePaint);
        thisPaint.setAlpha(Helper.randomNumber(20, 200));

        return new Blubber( thisPaint, Helper.randomNumber(maxSize, minSize), Helper.randomNumber(maxSpeed, minSpeed) );
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        thread = new mThread(this);
        thread.setRunning(true);
        thread.start();
        Log.i(TAG, "onSurfaceTextureAvailable");
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.i(TAG, "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.i(TAG, "onSurfaceTextureDestroyed");
        if (thread != null) thread.stopRendering();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //Log.i(TAG, "onSurfaceTextureUpdated");
    }


    class mThread extends Thread
    {
        private boolean run = false;
        private final Object runLock = new Object();
        private final Object waitLock = new Object();
        private final TextureView surface;

        public mThread(TextureView surface)
        {
            STATE_CURRENT = STATE_INITIALIZE;
            this.surface = surface;
        }


        public void run()
        {
            long startTime;
            long drawTime;

            while (run && !Thread.interrupted())
            {
                final Canvas canvas = surface.lockCanvas();
                if (STATE_CURRENT != STATE_PAUSE)
                {
                    startTime = System.currentTimeMillis();
                    try {
                        if (canvas != null)
                        {
                            drawBackground(canvas);
                            drawCircles(canvas);
                        }
                    }finally {
                        if (canvas != null)
                        {
                            unlockCanvasAndPost(canvas);
                        }
                    }

                    drawTime = (System.currentTimeMillis() - startTime);
                    if (drawTime <= FRAME_PERIOD && STATE_CURRENT != STATE_INITIALIZE)
                    {
                        try {
                            Log.i(TAG, "sleep: "+(FRAME_PERIOD - drawTime));
                            sleep(FRAME_PERIOD - drawTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    try {
                        synchronized (waitLock)
                        {
                            waitLock.wait();
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void setRunning(boolean b)
        {
            synchronized (runLock)
            {
                run = b;
            }
        }

        public void stopRendering()
        {
            interrupt();
            setRunning(false);
        }
    }

}
