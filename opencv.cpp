#include <complex>// nbr complex
#include <opencv2/opencv.hpp>
#include <thread>
#include<chrono>// pour calc du temps
#define IMG_W 800
#define IMG_H 600
std::complex<long double> c(-0.7269,0.1889);

std::thread tabThread[IMG_W];// on crée un tb de th de taille de W
cv::Mat newImg;// image de type MAT
cv::Vec3b rabidyhia(long double i, long double j){
  cv::Vec3b monpixel(255, 255, 255);
  i=( (long double) ((long double)i- ((long double)IMG_W/2))/((long double)IMG_W/2));// transposé i entre (-1 et 1)
  j=( (long double) ((long double)j- ((long double)IMG_W/2))/((long double)IMG_W/2));
  std::complex<long double> z(i, j);// l'initialisation du point
  int k = 0;// nombre d'itération 
  for(; k<255 && norm(z) < 4; k++){// 
      z = z*z+c;   
     }
  monpixel = cv::Vec3b(k, k, k);
  return monpixel;
}

void juliaThread(int x){// la fc de th, chaque th calcule tout les pixel y pour une position x
for(int y=0;y<IMG_H;y++){
  newImg.at<cv::Vec3b>(x,y)=rabidyhia((long double)x,(long double)y);// il calcule tt les y
}
}


/*void juliaNormal(){// la fc de th, chaque th calcule tout les pixel y pour une position x
 for(int x=0;x<IMG_W;x++){
for(int y=0;y<IMG_H;y++){
  newImg.at<cv::Vec3b>(x,y)=rabidyhia((long double)x,(long double)y);// il calcule tt les y
 }
}
}*/




int main(int argc, char * argv[]) {
	// un nouvel objet image (ordre des couleurs : bleu/vert/rouge)
   newImg= cv::Mat(IMG_W, IMG_H, CV_8UC3);
   std::chrono::time_point<std::chrono::system_clock> start, end;
   start = std::chrono::system_clock::now();
   
   // intialise le temps

    for(int i=0;i<IMG_W;i++){
     tabThread[i]=std::thread(juliaThread,i); // intialise autant des th que W larg et je passe en paramettre le x qui doivent calculer 
   }

    //juliaNormal();
    end = std::chrono::system_clock::now();
    // initialise tp2
    long double temps=(std::chrono::duration_cast<std::chrono::milliseconds>(end-start).count());
    std::cout << "temps d'execution: "<< temps/(long double)1000<<" secondes"<<std::endl;

   //calcule t2 -t1
    // interaction avec l'utilisateur
    // key = -1 indique qu'aucune touche n'a été enfoncée
    while(int key = cvWaitKey(30)) {
    	switch (key) {
    		default:
    			break;
    	}
    	if (key == 'q')
    		return 0;
    	imshow("image", newImg); // affiche l'image
    }
    imwrite("image.jpg", newImg); // sauve une copie de l'image
	cvDestroyWindow("image"); // ferme la fenêtre

    return 0;
}
