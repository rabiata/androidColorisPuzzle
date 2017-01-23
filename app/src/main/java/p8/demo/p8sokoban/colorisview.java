package p8.demo.p8sokoban;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



public class colorisview extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    // Declaration des images


    // constante modelisant les differentes types de cases
    static final int CST_block = 0;
    static final int CST_diamant = 1;
    static final int CST_perso = 2;
    static final int CST_zone = 3;


    private Bitmap blue;
    private Bitmap vert;
    private Bitmap vide;

    private Bitmap blanc;
    private Bitmap rouge;
    private Bitmap jaune;
    private Bitmap mauve;

    private Bitmap cellVect_wite;
    private Bitmap cellVect_red;
    private Bitmap cellVect_yelow;
    private Bitmap cellVect_mauve;
    private Bitmap cellVect_blue;
    private Bitmap cellVect_green;

    private final static int nbofRandVector = 3;

    vecteur[] myVector = new vecteur[nbofRandVector];

    private static Map<Integer, Bitmap> images = new HashMap<Integer, Bitmap>();
    private static Map<Integer, Bitmap> cellVectorMap= new HashMap<Integer, Bitmap>();

    // Declaration des objets Ressources et Context permettant d'acc�der aux ressources de notre application et de les charger
    private Resources mRes;
    private Context mContext;

    // tableau modelisant la carte du jeu
    int[][] carte;

    // ancres pour pouvoir centrer la carte du jeu
    int carteTopAnchor;                   // coordonn�es en Y du point d'ancrage de notre carte
    int carteLeftAnchor;                  // coordonn�es en X du point d'ancrage de notre carte

    // taille de la carte
    static final int carteWidth = 8;
    static final int carteHeight = 8;
    // taille de la carte
    static final int carteTileSize = 40;
    // taille de la celelule de vecteur
    static final int vectCellSize = 30;

    // constante modelisant les differentes types de cases

    static final int CST_mauve = 6;
    static final int CST_vert = 5;
    static final int CST_blue = 4;
    static final int CST_blanc = 3;
    static final int CST_rouge = 2;
    static final int CST_jaune = 1;
    static final int CST_vide = 0;


    // init vect 1
// distance entre vecteur
    int depX = 115;

    // zone des vecteur

 // premier vec postion egal X=40
    int X1 = 40;
    int Y1 = 340;

    // Position initiale vecteurs




    // tableau de reference du terrain
    int[][] ref1 = {
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide},
            {CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide, CST_vide}

    };





    // thread utiliser pour animer les zones de depot des diamants
    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;

    //Paint paint;

    public colorisview(Context context, AttributeSet attrs) {
        super(context, attrs);


        // permet d'ecouter les surfaceChanged, surfaceCreated, surfaceDestroyed
        holder = getHolder();
        holder.addCallback(this);

        // chargement des images
        mContext = context;
        mRes = mContext.getResources();
        vide = BitmapFactory.decodeResource(mRes, R.drawable.apture1);
        blue = BitmapFactory.decodeResource(mRes, R.drawable.blue);
        rouge = BitmapFactory.decodeResource(mRes, R.drawable.rouge);
        jaune = BitmapFactory.decodeResource(mRes, R.drawable.jeune);
        vert = BitmapFactory.decodeResource(mRes, R.drawable.vert);
        blanc = BitmapFactory.decodeResource(mRes, R.drawable.blanc);
        mauve = BitmapFactory.decodeResource(mRes, R.drawable.mauve);
        cellVect_blue = BitmapFactory.decodeResource(mRes, R.drawable.cellvect_blue);
        cellVect_red = BitmapFactory.decodeResource(mRes, R.drawable.cellvect_rouge);
        cellVect_yelow = BitmapFactory.decodeResource(mRes, R.drawable.cellvect_jaune);
        cellVect_green = BitmapFactory.decodeResource(mRes, R.drawable.cellvect_vert);
        cellVect_wite = BitmapFactory.decodeResource(mRes, R.drawable.cellvect_blanc);
        cellVect_mauve = BitmapFactory.decodeResource(mRes, R.drawable.cellvect_mauve);




        // Init vector
        for (int i = 0; i < nbofRandVector; i++) {
            myVector[i] = new vecteur();
            myVector[i].x1 = X1 + i * depX;
            myVector[i].y1 = Y1;
            myVector[i].x2 = myVector[i].x1 + carteTileSize;
            myVector[i].y2 = myVector[i].y1 + 3 * carteTileSize;
            myVector[i].isHorizontal = false;
        }


        // init image map
        images.put(CST_vide, vide);
        images.put(CST_blanc, blanc);
        images.put(CST_rouge, rouge);
        images.put(CST_vert, vert);
        images.put(CST_blue, blue);
        images.put(CST_jaune, jaune);
        images.put(CST_mauve, mauve);

        // init cellVectoMap
        cellVectorMap.put(CST_blanc,cellVect_wite);
        cellVectorMap.put(CST_rouge,cellVect_red);
        cellVectorMap.put(CST_vert,cellVect_green);
        cellVectorMap.put(CST_blue,cellVect_blue);
        cellVectorMap.put(CST_jaune,cellVect_yelow);
        cellVectorMap.put(CST_mauve,cellVect_mauve);


        // initialisation des parmametres du jeu
        initparameters();

        // creation du thread
        cv_thread = new Thread(this);
        // prise de focus pour gestion des touches
        setFocusable(true);


    }

    // chargement du niveau a partir du tableau de reference du niveau
    private void loadlevel() {
        for (int i = 0; i < carteHeight; i++) {
            for (int j = 0; j < carteWidth; j++) {

                carte[j][i] = ref1[j][i];


            }
        }
    }
    private void rotate (vecteur mvect){
        if(mvect.isHorizontal){
            mvect.y1 = mvect.y1- vectCellSize;

            mvect.x2 = mvect.x1 + vectCellSize;
            mvect.y2 = mvect.y1 + 3*vectCellSize;
            mvect.isHorizontal=false;

            // change color
            int tmpCodecolor = mvect.codeColor1;
            mvect.codeColor1 = mvect.codeColor3;
            mvect.codeColor3=tmpCodecolor;

        }
        else { // vertical to horizontal
            mvect.y1 = mvect.y1+ vectCellSize;
            mvect.x2 = mvect.x1 + 3*vectCellSize;
            mvect.y2 = mvect.y1 + vectCellSize;
            mvect.isHorizontal=true;
        }


    }

    private void getrandomVector(vecteur vect) {
        Random generateur = new Random();

        int randomint = 1 + generateur.nextInt(7 - 1);
        vect.codeColor1 = randomint;


        randomint = 1 + generateur.nextInt(7 - 1);
        vect.codeColor2 = randomint;


        randomint = 1 + generateur.nextInt(7 - 1);
        vect.codeColor3 = randomint;



        vect.isHorizontal=false;

    }

    void getsmilarCartes(){
        // smila carte more than 3 cartes
        int i=0;
        int j=0;
        int nbOfsimilarFoundH=0;
        int nbOfsimilarFoundV=0;
        int nbOfsimilarFoundT=0;
        //cv_thread.sleep(40);

        while (i < carteHeight) {
            j=0;

            while (j < carteWidth)
            {
                int k=j+1;
                int l=i+1;
                //Log.i("SimCarte",Integer.toString(j));
                if(carte[i][j] != CST_vide) {
                   // while (k < carteWidth) {
                        while ( k < carteWidth && carte[i][j] == carte[i][k]){
                            k++;
                        }


                    while (  l < carteHeight && carte[i][j] == carte[l][j] ){
                        l++;
                    }
                    nbOfsimilarFoundH=k-j;

                    if(nbOfsimilarFoundH >=3){
                        Log.i("SimCarte H",Integer.toString(nbOfsimilarFoundH));
                        for(int m=j;m <k;m++)
                        {
                            carte[i][m]=CST_vide;
                        }
                    }



                    nbOfsimilarFoundV = l-i;

                    if(nbOfsimilarFoundV >=3){
                        Log.i("SimCarte V",Integer.toString(nbOfsimilarFoundV));
                        for(int m=i;m <l;m++)
                        {
                            carte[m][j]=CST_vide;
                        }
                    }

                    nbOfsimilarFoundH=0;
                    nbOfsimilarFoundV=0;

                    //}

                }
                j=k;
                //j++;
            }


            i++;
        }

    }



    // initialisation du jeu
    public void initparameters() {



        carte = new int[carteHeight][carteWidth];
        loadlevel();
        //vect = new int[3];
        //loadvect();
        for (int k = 0; k < nbofRandVector; k++) {
            getrandomVector(myVector[k]);
        }
        carteTopAnchor = (getHeight() - carteHeight * carteTileSize) / 2;
        carteLeftAnchor = (getWidth() - carteWidth * carteTileSize) / 2;

        if ((cv_thread != null) && (!cv_thread.isAlive())) {
            cv_thread.start();
            Log.e("-FCT-", "cv_thread.start()");
        }
    }





    // dessin de la carte du jeu
    private void paintcarte(Canvas canvas) {

       // canvas.drawBitmap(blue, 20, 20, null);
        for (int i = 0; i < carteHeight; i++) {
            for (int j = 0; j < carteWidth; j++) {
                canvas.drawBitmap(images.get(carte[i][j]), j * carteTileSize, i * carteTileSize, null);

            }
        }
    }


    private void paintvect(Canvas canvas) {
        for (int i = 0; i < nbofRandVector; i++) {
            if(myVector[i].isHorizontal) {
                canvas.drawBitmap(cellVectorMap.get(myVector[i].codeColor1), myVector[i].x1, myVector[i].y1, null);
                canvas.drawBitmap(cellVectorMap.get(myVector[i].codeColor2), myVector[i].x1 + vectCellSize, myVector[i].y1 , null);
                canvas.drawBitmap(cellVectorMap.get(myVector[i].codeColor3), myVector[i].x1 + 2 * vectCellSize, myVector[i].y1 , null);
            }
            else{
                canvas.drawBitmap(cellVectorMap.get(myVector[i].codeColor1), myVector[i].x1, myVector[i].y1, null);
                canvas.drawBitmap(cellVectorMap.get(myVector[i].codeColor2), myVector[i].x1, myVector[i].y1 + vectCellSize, null);
                canvas.drawBitmap(cellVectorMap.get(myVector[i].codeColor3), myVector[i].x1, myVector[i].y1 + 2 * vectCellSize, null);

            }
        }
    }


    // dessin des diamants
    //
    private boolean isWon() {
        for (int i = 0; i < 4; i++) {
            //if (!IsCell(diamants[i][1], diamants[i][0], CST_zone)) {
            return false;
        }


        return true;


    }

    // dessin du jeu (fond uni, en fonction du jeu gagne ou pas dessin du plateau et du joueur des diamants et des fleches)
    private void nDraw(Canvas canvas) {
        canvas.drawRGB(190, 97, 255);
        if (isWon()) {

            //paintcarte(canvas);

        } else {
            paintcarte(canvas);

            paintvect(canvas);
            getsmilarCartes();


        }

    }

    private int[] getcarteposition(int x, int y) {
        int j = x / carteTileSize;
        int i = y / carteTileSize;
        int[] position = {i, j};
        return position;

    }

    private int getSelectedVect(int x, int y) {
        int selectdVect = -1;

        for (int i = 0; i < nbofRandVector; i++) {
            if ((x > myVector[i].x1 && x < myVector[i].x2) &&
                    (y > myVector[i].y1 && y < myVector[i].y2)) {
                selectdVect = i;
            }
        }

        return selectdVect;

    }

    // callback sur le cycle de vie de la surfaceview
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("-> FCT <-", "surfaceChanged " + width + " - " + height);
        initparameters();
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceCreated");
    }


    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i("-> FCT <-", "surfaceDestroyed");
    }

    /**
     * run (run du thread cr��)
     * on endort le thread, on modifie le compteur d'animation, on prend la main pour dessiner et on dessine puis on lib�re le canvas
     */
    public void run() {
        Canvas c = null;
        while (in) {
            try {
                cv_thread.sleep(40);
                //currentStepZone = (currentStepZone + 1) % maxStepZone;
                try {
                    c = holder.lockCanvas(null);
                    nDraw(c);
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
            }
        }
    }

    // verification que nous sommes dans le tableau
    private boolean PosTab(int x, int y) {
        if ((x < 0) || (x > carteWidth - 1)) {
            return true;
        }
        if ((y < 0) || (y > carteHeight - 1)) {
            return true;
        }
        return false;
    }

    private boolean  insertVector(int i, int j,vecteur vect){

        if(vect.isHorizontal==true){
            for(int k=0;k<3;k++){
                if(carte[i][j+k] != CST_vide)
                {
                    int val=0;
                 return false  ;
                }

            }
            carte[i][j] = vect.codeColor1;
            carte[i][j + 1] = vect.codeColor2;
            carte[i][j + 2] = vect.codeColor3;
        }
        else{
            for(int k=0;k<3;k++){
                if(carte[i+k][j] != CST_vide){
                  return false;
                }
            }


            carte[i][j] = vect.codeColor1;
            carte[i+1][j] = vect.codeColor2;
            carte[i+2][j] = vect.codeColor3;

        }
        return true;

    }

    //controle de la valeur d'une cellule
    private boolean VerCartehori(int i, int j) {
        if ( (carte[i][j] == CST_vide)&&(carte[i+1][j] == CST_vide)&&(carte[i+2][j] == CST_vide) )
        {
            return true;
        }
        return false;
    }
    private boolean VerCartehvert(int i, int j) {
        if ( (carte[i][j] == CST_vide)&&(carte[i][j+1] == CST_vide)&&(carte[i][j+2] == CST_vide) )
        {
            return true;
        }
        return false;
    }



    int clickeY = 0; //(int) event.getY();
    int clickeX = 0; //(int) event.getX();
    int prevX = 0;
    int prevY = 0;
    int deltaX = 0;
    int deltaY = 0;
    int selectedVect = -1;

    // fonction permettant de recuperer les evenements tactiles
    public boolean onTouchEvent(MotionEvent event) {



        // final int t = event.getAction();
        // Check if vector is moved
        if (event.getAction() == MotionEvent.ACTION_DOWN) { //
            Log.i("-> FCT <-", "onTouchEvent: Down ");
            clickeY = (int) event.getY();
            clickeX = (int) event.getX();

            prevX = clickeX;
            prevY = clickeY;
            selectedVect = getSelectedVect(clickeX,clickeY);

        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.i("-> FCT <-", "onTouchEvent: Move" + Float.toString(event.getY()) + " " + Float.toString(event.getX()));
            // paint vector in position


                    deltaX = (int) event.getX() - prevX;
                    deltaY = (int) event.getY() - prevY;
                    prevX = (int) event.getX();
                    prevY = (int) event.getY();
                    Log.i("-> FCT Down <-", Integer.toString(deltaX) + " " + Integer.toString(deltaY));
                    myVector[selectedVect].x1 = myVector[selectedVect].x1 + deltaX;//(int) event.getX();
                    myVector[selectedVect].y1 = myVector[selectedVect].y1 + deltaY;//(int) event.getY();

        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i("-> FCT <-", "onTouchEvent: Up ");


            deltaX = (int) event.getX() - clickeX;
            deltaY = (int) event.getY() - clickeY;
            if (deltaX == 0 && deltaY == 0) { // click
                Log.i("-> FCT <-", "onTouchEvent: Up" + " " + Integer.toString(deltaX));
                if(selectedVect != -1) {
                    // rotate selected vector
                    rotate(myVector[selectedVect]);
                }


            } else { //Move
               if (selectedVect != -1) {

                   int putcarte[] = getcarteposition(myVector[selectedVect].x1, myVector[selectedVect].y1);

                   // Insert vector in matrice
                   if(insertVector(putcarte[0],putcarte[1],myVector[selectedVect])){
                       getrandomVector(myVector[selectedVect]);
                   }


                   myVector[selectedVect].x1 = X1 + selectedVect * depX;
                   myVector[selectedVect].y1 = Y1;
                   selectedVect = -1;

               }



                }

            }

            return true;
        }

    }




