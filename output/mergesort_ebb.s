.data
.text
.ent FUNC_mergesort
.globl FUNC_mergesort
FUNC_mergesort:
addi $sp, $sp, 216
sw $s1, -16($sp)
sw $s0, -12($sp)
sw $s2, -8($sp)
sw $s3, -4($sp)
sw $s4, 0($sp)
sw $fp, -208($sp)
sw $ra, -212($sp)
lw $t0, -204($sp)
lw $t1, -200($sp)
lw $t2, -196($sp)
lw $t3, -192($sp)
lw $t4, -188($sp)
lw $t5, -144($sp)
lw $s1, -140($sp)
lw $t6, -136($sp)
lw $s0, -216($sp)
lw $s2, -132($sp)
lw $s3, -88($sp)
lw $s4, -84($sp)
li $t7, 0

addi $s2, $sp, -132
addi $s2, $s2, 4
sw $s2, -132($sp)
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
sw $t7, 0($s2)
addi $s2, $s2, 4
lw $s2, -132($sp)
li $t7, 0

addi $t4, $sp, -188
addi $t4, $t4, 4
sw $t4, -188($sp)
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
sw $t7, 0($t4)
addi $t4, $t4, 4
lw $t4, -188($sp)
li $t7, 0

move $t6, $t7

li $t7, 0

move $t2, $t7

li $t7, 0

move $t0, $t7

li $t7, 0

move $t1, $t7

li $t7, 1
ble  $s0, $t7, LABEL_ELSE_BEGIN_0

li $t7, 2
div $s3, $s0, $t7

move $t1, $s3

sub $s4, $s0, $t1

move $t0, $s4

li $t7, 1
sub $t3, $t1, $t7

move $s1, $t3

li $t7, 0

move $t5, $t7

sw $t0, -204($sp)
sw $t1, -200($sp)
sw $t2, -196($sp)
sw $t3, -192($sp)
sw $t4, -188($sp)
sw $t5, -144($sp)
sw $s1, -140($sp)
sw $t6, -136($sp)
sw $s0, -216($sp)
sw $s2, -132($sp)
sw $s3, -88($sp)
sw $s4, -84($sp)

LABEL_FOR_START_0:
lw $t0, -80($sp)
lw $t1, -204($sp)
lw $t2, -76($sp)
lw $t3, -188($sp)
lw $t4, -220($sp)
lw $t5, -144($sp)
lw $t6, -140($sp)
lw $s0, -72($sp)
lw $s1, -68($sp)
lw $s2, -64($sp)
bgt  $t5, $t6, LABEL_FOR_END_0


add $t4, $t4, $t5
add $t4, $t4, $t5
add $t4, $t4, $t5
add $t4, $t4, $t5
lw $s0, 0($t4)
lw $t4, -220($sp)

add $t3, $t3, $t5
add $t3, $t3, $t5
add $t3, $t3, $t5
add $t3, $t3, $t5
sw $s0, 0($t3)
lw $t3, -188($sp)
li $t7, 1
add $s2, $t5, $t7

move $t5, $s2

sw $t0, -80($sp)
sw $t1, -204($sp)
sw $t2, -76($sp)
sw $t3, -188($sp)
sw $t4, -220($sp)
sw $t5, -144($sp)
sw $t6, -140($sp)
sw $s0, -72($sp)
sw $s1, -68($sp)
sw $s2, -64($sp)
j LABEL_FOR_START_0

LABEL_FOR_END_0:
li $t7, 1
sub $t0, $t1, $t7

move $t2, $t0

li $t7, 0

move $s1, $t7

sw $t0, -80($sp)
sw $t1, -204($sp)
sw $t2, -76($sp)
sw $t3, -188($sp)
sw $t4, -220($sp)
sw $t5, -144($sp)
sw $t6, -140($sp)
sw $s0, -72($sp)
sw $s1, -68($sp)
sw $s2, -64($sp)

LABEL_FOR_START_1:
lw $t0, -60($sp)
lw $t1, -200($sp)
lw $t2, -56($sp)
lw $t3, -76($sp)
lw $t4, -132($sp)
lw $t6, -220($sp)
lw $t5, -52($sp)
lw $s0, -68($sp)
bgt  $s0, $t3, LABEL_FOR_END_1

add $t0, $s0, $t1

add $t6, $t6, $t0
add $t6, $t6, $t0
add $t6, $t6, $t0
add $t6, $t6, $t0
lw $t5, 0($t6)
lw $t6, -220($sp)

add $t4, $t4, $s0
add $t4, $t4, $s0
add $t4, $t4, $s0
add $t4, $t4, $s0
sw $t5, 0($t4)
lw $t4, -132($sp)
li $t7, 1
add $t2, $s0, $t7

move $s0, $t2

sw $t0, -60($sp)
sw $t1, -200($sp)
sw $t2, -56($sp)
sw $t3, -76($sp)
sw $t4, -132($sp)
sw $t6, -220($sp)
sw $t5, -52($sp)
sw $s0, -68($sp)
j LABEL_FOR_START_1

LABEL_FOR_END_1:
sw $t0, -60($sp)
sw $t1, -200($sp)
sw $t2, -56($sp)
sw $t3, -76($sp)
sw $t4, -132($sp)
sw $t6, -220($sp)
sw $t5, -52($sp)
sw $s0, -68($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t2, -28($sp)
sw $t3, -24($sp)
sw $t4, -20($sp)
sw $t5, -16($sp)
sw $t6, -12($sp)
sw $t7, -8($sp)

lw $a0, -228($sp)

sw $a0, -4($sp)

lw $a0, -240($sp)

sw $a0, 0($sp)
jal FUNC_mergesort
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t2, -28($sp)
lw $t3, -24($sp)
lw $t4, -20($sp)
lw $t5, -16($sp)
lw $t6, -12($sp)
lw $t7, -8($sp)
addi $sp, $sp, -40
sw $t0, -60($sp)
sw $t1, -200($sp)
sw $t2, -56($sp)
sw $t3, -76($sp)
sw $t4, -132($sp)
sw $t6, -220($sp)
sw $t5, -52($sp)
sw $s0, -68($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t2, -28($sp)
sw $t3, -24($sp)
sw $t4, -20($sp)
sw $t5, -16($sp)
sw $t6, -12($sp)
sw $t7, -8($sp)

lw $a0, -172($sp)

sw $a0, -4($sp)

lw $a0, -244($sp)

sw $a0, 0($sp)
jal FUNC_mergesort
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t2, -28($sp)
lw $t3, -24($sp)
lw $t4, -20($sp)
lw $t5, -16($sp)
lw $t6, -12($sp)
lw $t7, -8($sp)
addi $sp, $sp, -40

LABEL_WHILE_TOP_0:
lw $t0, -196($sp)
lw $t1, -200($sp)
lw $t2, -136($sp)
lw $t3, -204($sp)
sw $t0, -196($sp)
sw $t1, -200($sp)
sw $t2, -136($sp)
sw $t3, -204($sp)
blt  $t0, $t1, LABEL_WHILE_BEGIN_0

sw $t0, -196($sp)
sw $t1, -200($sp)
sw $t2, -136($sp)
sw $t3, -204($sp)
bge  $t2, $t3, LABEL_WHILE_END_0

LABEL_WHILE_BEGIN_0:
lw $t0, -48($sp)
lw $t1, -200($sp)
lw $t2, -196($sp)
lw $t3, -188($sp)
lw $t4, -44($sp)
lw $t5, -220($sp)
lw $t6, -136($sp)
lw $s0, -132($sp)
lw $s1, -40($sp)
lw $s2, -36($sp)
lw $s3, -32($sp)
sw $t0, -48($sp)
sw $t1, -200($sp)
sw $t2, -196($sp)
sw $t3, -188($sp)
sw $t4, -44($sp)
sw $t5, -220($sp)
sw $t6, -136($sp)
sw $s0, -132($sp)
sw $s1, -40($sp)
sw $s2, -36($sp)
sw $s3, -32($sp)
beq  $t2, $t1, LABEL_IF_START_1


add $s0, $s0, $t6
add $s0, $s0, $t6
add $s0, $s0, $t6
add $s0, $s0, $t6
lw $s1, 0($s0)
lw $s0, -132($sp)

add $t3, $t3, $t2
add $t3, $t3, $t2
add $t3, $t3, $t2
add $t3, $t3, $t2
lw $s3, 0($t3)
lw $t3, -188($sp)
sw $t0, -48($sp)
sw $t1, -200($sp)
sw $t2, -196($sp)
sw $t3, -188($sp)
sw $t4, -44($sp)
sw $t5, -220($sp)
sw $t6, -136($sp)
sw $s0, -132($sp)
sw $s1, -40($sp)
sw $s2, -36($sp)
sw $s3, -32($sp)
bge  $s1, $s3, LABEL_ELSE_BEGIN_1

LABEL_IF_START_1:
lw $t1, -28($sp)
lw $t0, -196($sp)
lw $t2, -24($sp)
lw $t3, -136($sp)
lw $t5, -20($sp)
lw $t4, -132($sp)
lw $t6, -220($sp)
add $t2, $t0, $t3

add $t4, $t4, $t3
add $t4, $t4, $t3
add $t4, $t4, $t3
add $t4, $t4, $t3
lw $t1, 0($t4)
lw $t4, -132($sp)

add $t6, $t6, $t2
add $t6, $t6, $t2
add $t6, $t6, $t2
add $t6, $t6, $t2
sw $t1, 0($t6)
lw $t6, -220($sp)
li $t7, 1
add $t5, $t3, $t7

move $t3, $t5

sw $t1, -28($sp)
sw $t0, -196($sp)
sw $t2, -24($sp)
sw $t3, -136($sp)
sw $t5, -20($sp)
sw $t4, -132($sp)
sw $t6, -220($sp)
j LABEL_ELSE_END_0

LABEL_ELSE_BEGIN_1:
add $s2, $t2, $t6

add $t3, $t3, $t2
add $t3, $t3, $t2
add $t3, $t3, $t2
add $t3, $t3, $t2
lw $t0, 0($t3)
lw $t3, -188($sp)

add $t5, $t5, $s2
add $t5, $t5, $s2
add $t5, $t5, $s2
add $t5, $t5, $s2
sw $t0, 0($t5)
lw $t5, -220($sp)
li $t7, 1
add $t4, $t2, $t7

move $t2, $t4

sw $t0, -48($sp)
sw $t1, -200($sp)
sw $t2, -196($sp)
sw $t3, -188($sp)
sw $t4, -44($sp)
sw $t5, -220($sp)
sw $t6, -136($sp)
sw $s0, -132($sp)
sw $s1, -40($sp)
sw $s2, -36($sp)
sw $s3, -32($sp)

LABEL_ELSE_END_0:
j LABEL_WHILE_TOP_0

LABEL_WHILE_END_0:
sw $t0, -196($sp)
sw $t1, -200($sp)
sw $t2, -136($sp)
sw $t3, -204($sp)
j LABEL_ELSE_END_1

LABEL_ELSE_BEGIN_0:
sw $t0, -204($sp)
sw $t1, -200($sp)
sw $t2, -196($sp)
sw $t3, -192($sp)
sw $t4, -188($sp)
sw $t5, -144($sp)
sw $s1, -140($sp)
sw $t6, -136($sp)
sw $s0, -216($sp)
sw $s2, -132($sp)
sw $s3, -88($sp)
sw $s4, -84($sp)

LABEL_ELSE_END_1:
lw $fp, -208($sp)
lw $ra, -212($sp)
lw $s1, -16($sp)
lw $s0, -12($sp)
lw $s2, -8($sp)
lw $s3, -4($sp)
lw $s4, 0($sp)
addi $sp, $sp, -216
jr $ra


.end FUNC_mergesort
.ent main
.globl main
main:
addi $sp, $sp, 68
sw $fp, -60($sp)
sw $ra, -64($sp)
lw $t0, -56($sp)
lw $t1, -52($sp)
lw $t2, -8($sp)
li $t7, 0

addi $t1, $sp, -52
addi $t1, $t1, 4
sw $t1, -52($sp)
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
sw $t7, 0($t1)
addi $t1, $t1, 4
lw $t1, -52($sp)
li $t7, 0
li $t8, 5

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t7, 1
li $t8, 4

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t7, 2
li $t8, 6

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t8, 3

add $t1, $t1, $t8
add $t1, $t1, $t8
add $t1, $t1, $t8
add $t1, $t1, $t8
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t8, 7
li $t7, 4

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t8, 2
li $t7, 5

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t7, 6
li $t8, 8

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t8, 1
li $t7, 7

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t8, 9
li $t7, 8

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
li $t8, 0
li $t7, 9

add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
add $t1, $t1, $t7
sw $t8, 0($t1)
lw $t1, -52($sp)
sw $t0, -56($sp)
sw $t1, -52($sp)
sw $t2, -8($sp)
addi $sp, $sp, 40
sw $t0, -36($sp)
sw $t1, -32($sp)
sw $t2, -28($sp)
sw $t3, -24($sp)
sw $t4, -20($sp)
sw $t5, -16($sp)
sw $t6, -12($sp)
sw $t7, -8($sp)

lw $a0, -92($sp)

sw $a0, -4($sp)

li $a0, 10
sw $a0, 0($sp)
jal FUNC_mergesort
lw $t0, -36($sp)
lw $t1, -32($sp)
lw $t2, -28($sp)
lw $t3, -24($sp)
lw $t4, -20($sp)
lw $t5, -16($sp)
lw $t6, -12($sp)
lw $t7, -8($sp)
addi $sp, $sp, -40
li $t7, 9

move $t0, $t7

li $t7, 0

move $t2, $t7

sw $t0, -56($sp)
sw $t1, -52($sp)
sw $t2, -8($sp)

LABEL_FOR_START_2:
lw $t1, -56($sp)
lw $t0, -4($sp)
lw $t2, -8($sp)
lw $t3, 0($sp)
bgt  $t2, $t1, LABEL_FOR_END_2

li $t7, arr
