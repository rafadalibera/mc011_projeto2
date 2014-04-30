@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * * %tmp0
  %tmp1 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp2 = call i32 (i8 *, ...)* @printf(i8 * %tmp1, i32 10)
  %tmp3 = load i32 * %tmp0
  ret i32 %tmp3
}
%class.k = { i32, i32 }
define i32 @__TestMethod_k(%class.k * %this, i32 argsa, i1 argsb) {
  %tmp4 = alloca i32
  store i32 argsa, i32 * %tmp4
  %tmp5 = alloca i1
  store i1 argsb, i1 * %tmp5
  %dd_temp = alloca i32
  %ee_temp = alloca i32
  store i32 3, i32 * %ee_temp
  %tmp6 = icmp eq i32 null, 2
 %tmp7 = mul i32 %tmp6, %tmp6
  br i1 %tmp7, label %label0, label %label1
label0:
  store i32 3, i32 * %dd_temp
  br label %label2
label1:
  store i32 2, i32 * %dd_temp
  br label %label2
label2:
  ret i32 3
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
