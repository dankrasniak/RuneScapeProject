opencv_traincascade -data classifier -vec sr.vec -bg n.txt -numStages 7 -numPos 390 -numNeg 1500 -w 20 -h 20 -minHitRate 0.999 -maxFalseAlarmRate 0.1 