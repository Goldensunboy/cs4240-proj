.data
.text
.ent FUNC_mergesort
.globl FUNC_mergesort
FUNC_mergesort:
addi $sp, $sp, 196
sw $fp, -188($sp)
sw $ra, -192($sp)
li $t7, 0

addi $t8, $sp, -184
addi $t8, $t8, 4
sw $t8, -184($sp)
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
lw $t8, -184($sp)
sw $t8, -184($sp)
li $t7, 0

addi $t8, $sp, -140
addi $t8, $t8, 4
sw $t8, -140($sp)
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
lw $t8, -140($sp)
sw $t8, -140($sp)
li $t7, 0

move $t8, $t7

sw $t8, -96($sp)
li $t7, 0

move $t8, $t7

sw $t8, -92($sp)
li $t7, 0

move $t8, $t7

sw $t8, -88($sp)
li $t7, 0

move $t8, $t7

sw $t8, -84($sp)
li $t8, 1
lw $t7, -196($sp)
ble  $t7, $t8, LABEL_ELSE_BEGIN_0
li $t8, 2
lw $t7, -196($sp)
div $t9, $t7, $t8
sw $t9, -80($sp)
lw $t7, -80($sp)

move $t8, $t7

sw $t8, -84($sp)
lw $t7, -196($sp)
lw $t8, -84($sp)
sub $t9, $t7, $t8
sw $t9, -76($sp)
lw $t7, -76($sp)

move $t8, $t7

sw $t8, -88($sp)
li $t8, 1
lw $t7, -84($sp)
sub $t9, $t7, $t8
sw $t9, -72($sp)
lw $t7, -72($sp)

move $t8, $t7

sw $t8, -68($sp)
li $t7, 0

move $t8, $t7

sw $t8, -64($sp)
LABEL_FOR_START_0:
lw $t8, -68($sp)
lw $t7, -64($sp)
bgt  $t7, $t8, LABEL_FOR_END_0
lw $t9, -60($sp)
lw $t8, -64($sp)
lw $t7, -200($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -200($sp)
sw $t9, -60($sp)
lw $t9, -140($sp)
lw $t8, -60($sp)
lw $t7, -64($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -140($sp)
sw $t9, -140($sp)
li $t8, 1
lw $t7, -64($sp)
add $t9, $t7, $t8
sw $t9, -56($sp)
lw $t7, -56($sp)

move $t8, $t7

sw $t8, -64($sp)
j LABEL_FOR_START_0
LABEL_FOR_END_0:
li $t8, 1
lw $t7, -88($sp)
sub $t9, $t7, $t8
sw $t9, -52($sp)
lw $t7, -52($sp)

move $t8, $t7

sw $t8, -48($sp)
li $t7, 0

move $t8, $t7

sw $t8, -44($sp)
LABEL_FOR_START_1:
lw $t8, -48($sp)
lw $t7, -44($sp)
bgt  $t7, $t8, LABEL_FOR_END_1
lw $t7, -44($sp)
lw $t8, -84($sp)
add $t9, $t7, $t8
sw $t9, -40($sp)
lw $t9, -36($sp)
lw $t8, -40($sp)
lw $t7, -200($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -200($sp)
sw $t9, -36($sp)
lw $t9, -184($sp)
lw $t7, -44($sp)
lw $t8, -36($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -184($sp)
sw $t9, -184($sp)
li $t8, 1
lw $t7, -44($sp)
add $t9, $t7, $t8
sw $t9, -32($sp)
lw $t7, -32($sp)

move $t8, $t7

sw $t8, -44($sp)
j LABEL_FOR_START_1
LABEL_FOR_END_1:
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -160($sp)

sw $a0, -4($sp)

lw $a0, -104($sp)

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

lw $a0, -204($sp)

sw $a0, -4($sp)

lw $a0, -108($sp)

sw $a0, 0($sp)
jal FUNC_mergesort
lw $t7, -16($sp)
lw $t8, -12($sp)
lw $t9, -8($sp)
addi $sp, $sp, -20
LABEL_WHILE_TOP_0:
lw $t8, -84($sp)
lw $t7, -92($sp)
blt  $t7, $t8, LABEL_WHILE_BEGIN_0
lw $t8, -88($sp)
lw $t7, -96($sp)
bge  $t7, $t8, LABEL_WHILE_END_0
LABEL_WHILE_BEGIN_0:
lw $t8, -84($sp)
lw $t7, -92($sp)
beq  $t7, $t8, LABEL_IF_START_1
lw $t9, -28($sp)
lw $t8, -96($sp)
lw $t7, -184($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -184($sp)
sw $t9, -28($sp)
lw $t9, -24($sp)
lw $t7, -140($sp)
lw $t8, -92($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -140($sp)
sw $t9, -24($sp)
lw $t7, -28($sp)
lw $t8, -24($sp)
bge  $t7, $t8, LABEL_ELSE_BEGIN_1
LABEL_IF_START_1:
lw $t8, -96($sp)
lw $t7, -92($sp)
add $t9, $t7, $t8
sw $t9, -20($sp)
lw $t9, -16($sp)
lw $t8, -96($sp)
lw $t7, -184($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -184($sp)
sw $t9, -16($sp)
lw $t9, -200($sp)
lw $t8, -16($sp)
lw $t7, -20($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -200($sp)
sw $t9, -200($sp)
li $t8, 1
lw $t7, -96($sp)
add $t9, $t7, $t8
sw $t9, -12($sp)
lw $t7, -12($sp)

move $t8, $t7

sw $t8, -96($sp)
j LABEL_ELSE_END_0
LABEL_ELSE_BEGIN_1:
lw $t8, -96($sp)
lw $t7, -92($sp)
add $t9, $t7, $t8
sw $t9, -8($sp)
lw $t9, -4($sp)
lw $t7, -140($sp)
lw $t8, -92($sp)

add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
add $t7, $t7, $t8
lw $t9, 0($t7)
lw $t7, -140($sp)
sw $t9, -4($sp)
lw $t9, -200($sp)
lw $t8, -4($sp)
lw $t7, -8($sp)

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -200($sp)
sw $t9, -200($sp)
li $t8, 1
lw $t7, -92($sp)
add $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)

move $t8, $t7

sw $t8, -92($sp)
LABEL_ELSE_END_0:
j LABEL_WHILE_TOP_0
LABEL_WHILE_END_0:
j LABEL_ELSE_END_1
LABEL_ELSE_BEGIN_0:
LABEL_ELSE_END_1:
lw $fp, -188($sp)
lw $ra, -192($sp)
addi $sp, $sp, -196
jr $ra

.end FUNC_mergesort
.ent main
.globl main
main:
addi $sp, $sp, 68
sw $fp, -60($sp)
sw $ra, -64($sp)
li $t7, 0

addi $t8, $sp, -56
addi $t8, $t8, 4
sw $t8, -56($sp)
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
lw $t8, -56($sp)
sw $t8, -56($sp)
lw $t9, -56($sp)
li $t7, 0
li $t8, 5

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t7, 1
li $t8, 4

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t7, 2
li $t8, 6

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t8, 3

add $t9, $t9, $t8
add $t9, $t9, $t8
add $t9, $t9, $t8
add $t9, $t9, $t8
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t8, 7
li $t7, 4

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t8, 2
li $t7, 5

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t7, 6
li $t8, 8

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t8, 1
li $t7, 7

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t8, 9
li $t7, 8

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
lw $t9, -56($sp)
li $t8, 0
li $t7, 9

add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
add $t9, $t9, $t7
sw $t8, 0($t9)
lw $t9, -56($sp)
sw $t9, -56($sp)
addi $sp, $sp, 20
sw $t7, -16($sp)
sw $t8, -12($sp)
sw $t9, -8($sp)

lw $a0, -76($sp)

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
li $t7, arr
lw $t8, -8($sp)
