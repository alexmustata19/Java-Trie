.PHONY: build clean run
build:
	javac -d . src/trie/*.java src/test/*.java
clean:
	rm -r *.class
run:
	java -Xmx512m trie.MainClass