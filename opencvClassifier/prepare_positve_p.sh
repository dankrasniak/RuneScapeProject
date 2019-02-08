source values.config
rm p.txt
touch p.txt
for (( i=0; i <= $n_pos; i++ )); do 
	echo -e "/positive/image$i.bmp 1 0 0 30 30\r" >> p.txt;
done
