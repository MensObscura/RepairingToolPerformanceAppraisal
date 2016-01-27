#bin/bash



#DL sources nopol intro-class finch epr-asm
mkdir soft
cd soft

git clone https://github.com/SpoonLabs/nopol.git

git clone https://github.com/eschulte/asm.git

wget http://finch.cs.bgu.ac.il/dl/finch.zip
unzip finch.zip -d .
rm finch.zip

git clone https://github.com/Spirals-Team/IntroClassJava.git

#git clone git://orgmode.org/org-mode.git
#apt-get install  tangled
#git clone https://github.com/technomancy/leiningen.git


#Compiling Nopol

export JAVA_HOME=/usr/lib/java-7-openjdk-amd64

mvn install -Dmaven.test.skip=true 

export JAVA_HOME=/usr/lib/java-8-oracle



#Running all IntroClass

#mvn test

#java -jar nopol-0.0.3-SNAPSHOT-jar-with-dependencies.jar -s src/main/java/:src/test/#java/ -c target/test-classes/:target/classes/

#Change variable
#rootJavaIntroclass= os.path.dirname(__file__) + "./../dataset"
#rootIntroclass="/local/rosa/IDL/Genetic/IntroClassJava"
#python evalIntroClassJava.py 

