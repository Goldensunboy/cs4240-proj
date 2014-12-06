.data
.text

.end main
.ent main
.globl main
main:
addi $sp, $sp, 40
sw $fp, -32($sp)
sw $ra, -36($sp)
li $t7, 0
move $t8, $t7
sw $t8, -28($sp)
li $t7, 0
move $t8, $t7
sw $t8, -24($sp)
li $t7, 0
move $t8, $t7
sw $t8, -20($sp)
li $t7, 0
move $t8, $t7
sw $t8, -16($sp)
li $t7, 0
move $t8, $t7
sw $t8, -12($sp)
li $t7, 1
move $t8, $t7
sw $t8, -16($sp)
li $t7, 5
move $t8, $t7
sw $t8, -28($sp)
lw $t7, -12($sp)
lw $t8, -16($sp)
add $t9, $t7, $t8
sw $t9, -8($sp)
lw $t7, -8($sp)
move $t8, $t7
sw $t8, -20($sp)
LABEL_WHILE_TOP_0:
li $t8, 0
lw $t7, -28($sp)
ble  $t7, $t8, LABEL_WHILE_END_0
lw $t7, -20($sp)
move $t8, $t7
sw $t8, -24($sp)
lw $t8, -12($sp)
lw $t7, -20($sp)
add $t9, $t7, $t8
sw $t9, -4($sp)
lw $t7, -4($sp)
move $t8, $t7
sw $t8, -20($sp)
lw $t7, -12($sp)
move $t8, $t7
sw $t8, -16($sp)
lw $t7, -24($sp)
move $t8, $t7
sw $t8, -12($sp)
li $t8, 1
lw $t7, -28($sp)
sub $t9, $t7, $t8
sw $t9, 0($sp)
lw $t7, 0($sp)
move $t8, $t7
sw $t8, -28($sp)
j LABEL_WHILE_TOP_0
LABEL_WHILE_END_0:
lw $fp, -32($sp)
lw $ra, -36($sp)
addi $sp, $sp, -40
jr $ra

