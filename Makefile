#SHELL := /bin/sh
SRC = ./src
BIN = ./testBin
LIBJAR = CAS.jar
LIBRUNJAR = CAS_CUP-runtime_include.jar
LIBDIR = ./CAS


CAS_CUP-runtime_include.jar: CAS_Setup
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ CAS_CUP-runtime_include.jar ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	cp java-cup-11a-runtime.jar $(LIBDIR) ;\
	cd $(LIBDIR) ;\
	jar -xf java-cup-11a-runtime.jar java_cup ;\
	rm java-cup-11a-runtime.jar ;\
	jar cf $(LIBRUNJAR) . ;\
	mv $(LIBRUNJAR) ../.

CAS_Setup: makeClassFiles makeCAS
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ CAS_Setup ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	cp -r $(BIN)/* $(LIBDIR)/. ;\

CAS.jar: CAS_Setup
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ CAS.jar ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	cd $(LIBDIR) ;\
	jar cf $(LIBJAR) . ;\
	mv $(LIBJAR) ../.

makeClassFiles: makeBIN makeFromCUPandLEX
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ makeClassFiles ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	cd $(BIN) ;\
	find -name "*.java" > srcFiles.txt ;\
	javac -classpath .:../java-cup-11a.jar @srcFiles.txt ;\
	cat srcFiles.txt | xargs rm ;\
	rm srcFiles.txt;\

makeCAS:
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ makeCAS ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	if [ -e $(LIBDIR) ];	then \
		echo "$(LIBDIR) exists"; \
		echo "Removing $(LIBDIR)" ;\
		rm -r $(LIBDIR) ;\
	fi; \
	echo "Creating $(LIBDIR)"; \
	mkdir $(LIBDIR); \

makeBIN: $(SRC)
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ makeBIN ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	if [ -e $(BIN) ];	then \
		echo "$(BIN) exists"; \
	else \
		echo "Creating $(BIN)"; \
		mkdir $(BIN); \
	fi; \
	cp -ru $(SRC)/* $(BIN)/. ; \
	rm $(BIN)/cas/parser/*.java
makeFromCUPandLEX: getCUPandLEX
	@ \
	echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ makeFromCUPandLEX ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	cd $(BIN)/cas/parser ;\
	java -jar ../../../java-cup-11a.jar -symbols MataSym -parser MataParser Mata.cup ;\
	java -jar ../../../jflex-1.5.0.jar --nobak Mata.lex ;\


getCUPandLEX: makeBIN
	@ \
		echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~ getCUPandLEX ~~~~~~~~~~~~~~~~~~~~~~~~~~~" ;\
	pwd;\
	if [ -e "jflex-1.5.0.jar" ]; then \
		echo "jflex-1.5.0.jar exists" ; \
	else \
		echo "getting JLex.jar" ;\
		wget http://www.jflex.de/jflex-1.5.0.tar.gz ;\
		gunzip jflex-1.5.0.tar.gz; \
		tar xvf jflex-1.5.0.tar; \
		cp jflex/lib/jflex-1.5.0.jar . ;\
		\
		rm -r jflex ;\
		rm -r jflex-1.5.0 ;\
		rm -r jflex-1.5.0.tar ;\
		\
	fi; \
	\
	if [ -e "java-cup-11a.jar" ]; then \
			echo "java-cup-11a.jar exists" ; \
		else \
			echo "getting java-cup-11a.jar" ;\
			wget http://www2.cs.tum.edu/projects/cup/java-cup-11a.jar ;\
		fi; \
	\
	if [ -e "java-cup-11a-runtime.jar" ]; then \
			echo "java-cup-11a-runtime.jar exists" ; \
		else \
			echo "getting java-cup-11a-runtime.jar" ;\
			wget http://www2.cs.tum.edu/projects/cup/java-cup-11a-runtime.jar ;\
		fi; \
	\
	
clean:
	@ \
	if [ -e $(BIN) ]; then \
		rm -r $(BIN) ;\
	else \
		echo "$(BIN) does not exist" ;\
	fi;\
	\
	if [ -e $(LIBJAR) ]; then \
		rm -r $(LIBJAR) ;\
	else \
		echo "$(LIBJAR) does not exist" ;\
	fi;\
	\
	if [ -e $(LIBDIR) ]; then \
		rm -r $(LIBDIR) ;\
	else \
		echo "$(LIBDIR) does not exist" ;\
	fi;\
	

	
new: clean CAS.jar

cleanAll: clean
	@ \
	rm *.jar ;\
		
	
makebin: makeBIN

makeBin: makeBIN

makeclassfiles: makeClassFiles
	
