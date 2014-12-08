.data
.text
.ent FUNC_mergesort
.globl FUNC_mergesort
FUNC_mergesort:
addi $sp, $sp, 208
sw $fp, -200($sp)
sw $ra, -204($sp)
li $t7, 0

addi $t8, $sp, -196
addi $t8, $t8, 4
sw $t8, -196($sp)
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
lw $t8, -196($sp)
sw $t8, -196($sp)
li $t7, 0

addi $t8, $sp, -152
addi $t8, $t8, 4
sw $t8, -152($sp)
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
lw $t8, -152($sp)
sw $t8, -152($sp)
li $t7, 0

move $t8, $t7

sw $t8, -108($sp)
li $t7, 0

move $t8, $t7

sw $t8, -104($sp)
li $t7, 0

move $t8, $t7

sw $t8, -100($sp)
li $t7, 0

move $t8, $t7

sw $t8, -96($sp)
li $t7, 0

move $t8, $t7

sw $t8, -92($sp)
li $t7, 0

move $t8, $t7

sw $t8, -88($sp)
li $t8, 1
lw $t7, -208($sp)
ble  $t7, $t8, LABEL_ELSE_BEGIN_0
li $t8, 2
lw $t7, -208($sp)
div $t9, $t7, $t8
sw $t9, -84($sp)
lw $t7, -84($sp)

move $t8, $t7

sw $t8, -96($sp)
lw $t7, -208($sp)
lw $t8, -96($sp)
sub $t9, $t7, $t8
sw $t9, -80($sp)
lw $t7, -80($sp)

move $t8, $t7

sw $t8, -100($sp)
li $t8, 1
lw $t7, -96($sp)
sub $t9, $t7, $t8
sw $t9, -76($sp)
lw $t7, -76($sp)

move $t8, $t7

sw $t8, -72($sp)
li $t7, 0

move $t8, $t7

sw $t8, -68($sp)
LABEL_FOR_START_0:
lw $t8, -72($sp)
lw $t7, -68($sp)
bgt  $t7, $t8, LABEL_FOR_END_0
lw $t9, -64($sp)
lw $t8, -68($sp)
lw $t7, -212($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -212($sp)
sw $t9, -64($sp)
lw $t9, -152($sp)
lw $t8, -64($sp)
lw $t7, -68($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -152($sp)
sw $t9, -152($sp)
li $t8, 1
lw $t7, -68($sp)
add $t9, $t7, $t8
sw $t9, -60($sp)
lw $t7, -60($sp)

move $t8, $t7

sw $t8, -68($sp)
j LABEL_FOR_START_0
LABEL_FOR_END_0:
li $t8, 1
lw $t7, -100($sp)
sub $t9, $t7, $t8
sw $t9, -56($sp)
lw $t7, -56($sp)

move $t8, $t7

sw $t8, -52($sp)
li $t7, 0

move $t8, $t7

sw $t8, -48($sp)
LABEL_FOR_START_1:
lw $t8, -52($sp)
lw $t7, -48($sp)
bgt  $t7, $t8, LABEL_FOR_END_1
lw $t7, -48($sp)
lw $t8, -96($sp)
add $t9, $t7, $t8
sw $t9, -44($sp)
lw $t9, -40($sp)
lw $t8, -44($sp)
lw $t7, -212($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -212($sp)
sw $t9, -40($sp)
lw $t9, -196($sp)
lw $t7, -48($sp)
lw $t8, -40($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -196($sp)
sw $t9, -196($sp)
li $t8, 1
lw $t7, -48($sp)
add $t9, $t7, $t8
sw $t9, -36($sp)
lw $t7, -36($sp)

move $t8, $t7

sw $t8, -48($sp)
j LABEL_FOR_START_1
LABEL_FOR_END_1:
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -172($sp)

sw $a0, -4($sp)

lw $a0, -116($sp)

sw $a0, 0($sp)
jal FUNC_mergesort
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -216($sp)

sw $a0, -4($sp)

lw $a0, -120($sp)

sw $a0, 0($sp)
jal FUNC_mergesort
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
LABEL_WHILE_TOP_0:
lw $t8, -96($sp)
lw $t7, -104($sp)
blt  $t7, $t8, LABEL_WHILE_BEGIN_0
lw $t8, -100($sp)
lw $t7, -108($sp)
bge  $t7, $t8, LABEL_WHILE_END_0
LABEL_WHILE_BEGIN_0:
lw $t8, -96($sp)
lw $t7, -104($sp)
beq  $t7, $t8, LABEL_IF_START_1
lw $t9, -32($sp)
lw $t8, -108($sp)
lw $t7, -196($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -196($sp)
sw $t9, -32($sp)
lw $t9, -28($sp)
lw $t7, -152($sp)
lw $t8, -104($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -152($sp)
sw $t9, -28($sp)
lw $t7, -32($sp)
lw $t8, -28($sp)
bge  $t7, $t8, LABEL_ELSE_BEGIN_1
LABEL_IF_START_1:
lw $t8, -108($sp)
lw $t7, -104($sp)
add $t9, $t7, $t8
sw $t9, -24($sp)
lw $t7, -24($sp)

move $t8, $t7

sw $t8, -88($sp)
lw $t8, -108($sp)
lw $t7, -104($sp)
add $t9, $t7, $t8
sw $t9, -20($sp)
lw $t9, -16($sp)
lw $t8, -108($sp)
lw $t7, -196($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -196($sp)
sw $t9, -16($sp)
lw $t9, -212($sp)
lw $t7, -20($sp)
lw $t8, -16($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -212($sp)
sw $t9, -212($sp)
li $t8, 1
lw $t7, -108($sp)
add $t9, $t7, $t8
sw $t9, -12($sp)
lw $t7, -12($sp)

move $t8, $t7

sw $t8, -108($sp)
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_1:
lw $t8, -108($sp)
lw $t7, -104($sp)
add $t9, $t7, $t8
sw $t9, -8($sp)
lw $t9, -4($sp)
lw $t7, -152($sp)
lw $t8, -104($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -152($sp)
sw $t9, -4($sp)
lw $t9, -212($sp)
lw $t7, -8($sp)
lw $t8, -4($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -212($sp)
sw $t9, -212($sp)
li $t8, 1
lw $t7, -104($sp)
add $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)

move $t8, $t7

sw $t8, -104($sp)
LABEL_ELSE_END_0:
j LABEL_WHILE_TOP_0
LABEL_WHILE_END_0:
j LABEL_ELSE_END_1
LABEL_ELSE_BEGIN_0:
LABEL_ELSE_END_1:
lw $fp, -200($sp)
lw $ra, -204($sp)
addi $sp, $sp, -208
jr $ra

.end FUNC_mergesort
.ent main
.globl main
main:
addi $sp, $sp, 72
sw $fp, -64($sp)
sw $ra, -68($sp)
li $t7, 9

addi $t8, $sp, -60
addi $t8, $t8, 4
sw $t8, -60($sp)
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
sw $t7, 0($t8)
addi $t8, $t8, 4
lw $t8, -60($sp)
sw $t8, -60($sp)
li $t7, 0

move $t8, $t7

sw $t8, -16($sp)
lw $t9, -60($sp)
li $t7, 0
li $t8, 5

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t7, 1
li $t8, 4

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t7, 2
li $t8, 6

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t8, 3

add $t9, $t9, $t8
add $t9, $t9, $t8
add $t9, $t9, $t8
add $t9, $t9, $t8
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t8, 7
li $t7, 4

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t8, 2
li $t7, 5

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t7, 6
li $t8, 8

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t8, 1
li $t7, 7

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t8, 9
li $t7, 8

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
lw $t9, -60($sp)
li $t8, 0
li $t7, 9

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -60($sp)
sw $t9, -60($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -80($sp)

sw $a0, -4($sp)

li $a0, 10
sw $a0, 0($sp)
jal FUNC_mergesort
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
li $t7, 9

move $t8, $t7

sw $t8, -12($sp)
li $t7, 0

move $t8, $t7

sw $t8, -8($sp)
LABEL_FOR_START_2:
lw $t7, -8($sp)
lw $t8, -12($sp)
bgt  $t7, $t8, LABEL_FOR_END_2
lw $t9, -4($sp)
lw $t8, -8($sp)
lw $t7, -60($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -60($sp)
sw $t9, -4($sp)
lw $t7, -4($sp)

move $t8, $t7

sw $t8, -16($sp)
li $v0, 1
lw $a0, -16($sp)

syscall
li $t8, 1
lw $t7, -8($sp)
add $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)

move $t8, $t7

sw $t8, -8($sp)
j LABEL_FOR_START_2
LABEL_FOR_END_2:
lw $fp, -64($sp)
lw $ra, -68($sp)
addi $sp, $sp, -72
jr $ra

