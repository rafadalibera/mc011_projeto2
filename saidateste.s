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
%class.t = { i32 }
%class.k = { i32, i32, %class.t * }
define i32 @__TestMethod_k(%class.k * %this, i32 argsa, i1 argsb) {
  %tmp4 = alloca i32
  store i32 argsa, i32 * %tmp4
  %tmp5 = alloca i1
  store i1 argsb, i1 * %tmp5
  %tmp6 = alloca i32
  %tmp7 = alloca i32
  store i32 0, i32 * %tmp6
  store i32 3, i32 * %tmp7
label0:
  %tmp8 = load i32 * %tmp7
  %tmp9 = icmp slt i32 %tmp8, 7
  br i1 %tmp9, label %label1, label %label2
label1:
  %tmp10 = load i32 * %tmp6
  %tmp11 = load i32 * %tmp7
  %tmp12 = mul i32 %tmp10, %tmp11
  store i32 %tmp12, i32 * %tmp6
  %tmp13 = load i32 * %tmp7
  %tmp14 = add i32 %tmp13, 1
  store i32 %tmp14, i32 * %tmp7
  br label %label0
label2:
  %tmp15 = load i32 * %tmp6
  ret i32 %tmp15
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
