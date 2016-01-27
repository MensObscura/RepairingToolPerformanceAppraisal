#bin/bash



#DL sources nopol intro-class finch epr-asm


#git clone git://orgmode.org/org-mode.git
#apt-get install  tangled
#git clone https://github.com/technomancy/leiningen.git


#Compiling Nopol

#Change JAVA_HOME to 1.7
#mvn install -Dmaven.test.skip=true 

#Running all IntroClass

#mvn test

java -jar nopol-0.0.3-SNAPSHOT-jar-with-dependencies.jar -s src/main/java/:src/test/java/ -c target/test-classes/:target/classes/

#Change variable
#rootJavaIntroclass= os.path.dirname(__file__) + "./../dataset"
#rootIntroclass="/local/rosa/IDL/Genetic/IntroClassJava"
#python evalIntroClassJava.py 

