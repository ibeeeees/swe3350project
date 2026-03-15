#!/bin/bash
# Compile script for Company Z Employee Management System
# Requires: Java 8+ and MySQL Connector/J in lib/ directory

BASEDIR="$(cd "$(dirname "$0")" && pwd)"
SRC="$BASEDIR/src"
OUT="$BASEDIR/out"
LIB="$BASEDIR/lib"

# Check for MySQL connector
CLASSPATH="$SRC"
if ls "$LIB"/*.jar 1>/dev/null 2>&1; then
    for jar in "$LIB"/*.jar; do
        CLASSPATH="$CLASSPATH:$jar"
    done
fi

mkdir -p "$OUT"

echo "Compiling Java source files..."
find "$SRC" -name "*.java" > /tmp/sources.txt
javac -cp "$CLASSPATH" -d "$OUT" @/tmp/sources.txt

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo ""
    echo "To run the application:"
    echo "  java -cp \"$OUT:$LIB/*\" com.companyz.ems.Main"
    echo ""
    echo "To run tests:"
    echo "  java -cp \"$OUT:$LIB/*\" com.companyz.ems.test.TestRunner"
else
    echo "Compilation failed."
    exit 1
fi
