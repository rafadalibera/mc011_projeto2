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
  %tmp4 = alloca %class.k *
  store %class.k * %this, %class.k * * %tmp4
  %tmp5 = alloca i32
  store i32 argsa, i32 * %tmp5
  %tmp6 = alloca i1
  store i1 argsb, i1 * %tmp6
  %tmp7 = alloca i32
  %tmp8 = alloca i32
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
