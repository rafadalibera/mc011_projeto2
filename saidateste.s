@.formatting.string = private constant [4 x i8] c"%d\0A\00"
define i32 @main() {
entry:
  %tmp0 = alloca i32
  store i32 0, i32 * %tmp0
  %tmp1 = getelementptr [4 x i8] * @.formatting.string, i32 0, i32 0
  %tmp2 = call i32 (i8 *, ...)* @printf(i8 * %tmp1, i32 10)
  %tmp3 = load i32 * %tmp0
  ret i32 %tmp3
}
%class.k = { i32, i32 }
  argsa = alloca i32
  argsb = alloca i8
define i32 @__TestMethod_k(i32 argsa, i8 argsb) {
  argsa = alloca i32
  store i32 argsa, i32 argsa
  argsb = alloca i8
  store i8 argsb, i8 argsb
  %tmp4 = alloca i32
  %tmp5 = alloca i32
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
