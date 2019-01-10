

LIB = ../lib
SRCDIR = src
BINDIR = bin
DOCDIR = doc

JAVAC = javac
JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)

vpath %.java $(SRCDIR)
vpath %.class $(BINDIR)


# define general build rule for java sources
.SUFFIXES:  .java  .class

.java.class:
	$(JAVAC)  $(JFLAGS)  $<

#default rule - will be invoked by make

all:
	javac $(JFLAGS) $(SRCDIR)/*.java
		
clean:
	@rm -f  $(BINDIR)/*.class
