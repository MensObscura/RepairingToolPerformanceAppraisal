#bin/bash


#DL sources nopol intro-class finch epr-asm

mkdir soft
cd soft
path=`pwd`

git clone https://github.com/SpoonLabs/nopol.git

git clone https://github.com/eschulte/asm.git

wget http://finch.cs.bgu.ac.il/dl/finch.zip
unzip finch.zip -d .

git clone https://github.com/Spirals-Team/IntroClassJava.git


#Clean Trash sources

rm finch.zip


#Nopol Dependencies & requirements

wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz
tar xzvf jdk-7u79-linux-x64.tar.gz

java=$JAVA_HOME
export JAVA_HOME=$path/jdk-7u79-linux-x64/jre


#Compiling nopol

cd nopol

mvn install -Dmaven.test.skip=true 


#Clean nopol trash

cd ..
rm jdk-7u79-linux-x64.tar.gz
rm -rf jdk-7u79-linux-x64
export JAVA_HOME=$java


#epr-asm dependencies requirements

git clone git://orgmode.org/org-mode.git

git clone https://github.com/technomancy/leiningen.git
cd leiningen
git checkout stable
PATH=$PATH:$path/leiningen/bin/
chmod 755 $path/leiningen/bin/lein
cd leiningen-core/
lein bootstrap

apt-get install  tangled


#Compiling asm

cd ../asm/

lein deps

tangle neutral.org

lein jar


#add it to classpath




#Running all IntroClass

mvn test

java -jar nopol-0.0.3-SNAPSHOT-jar-with-dependencies.jar -s src/main/java/:src/test/#java/ -c target/test-classes/:target/classes/

#Change variable
#rootJavaIntroclass= os.path.dirname(__file__) + "./../dataset"
#rootIntroclass="/local/rosa/IDL/Genetic/IntroClassJava"
#python evalIntroClassJava.py 

