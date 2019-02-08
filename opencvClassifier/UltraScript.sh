n_pos=$(ls -1 ./positive | wc -l)
n_neg=$(ls -1 ./negative | wc -l)

rm p.txt
touch p.txt
for (( i=0; i < $n_pos; i++ )); do 
	echo -e "./positive/image$i.bmp 1 0 0 30 30\r" >> p.txt;
done

rm n.txt
touch n.txt
for (( i=0; i < $n_neg; i++ )); do 
	echo -e "./negative/image$i.bmp\r" >> n.txt;
done