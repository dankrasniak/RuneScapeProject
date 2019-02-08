source values.config
rm n.txt
touch n.txt
for (( i=0; i <= $n_neg; i++ )); do 
	echo -e "/negative/image$i.bmp\r" >> n.txt;
done
