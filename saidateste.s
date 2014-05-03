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
%class.good = type { i32, i32 }
define i32 @__func_good(%class.good * %this) {
  %tmp4 = getelementptr %class.good * %this, i32 0, i32 0
  store i32 2, i32 * %tmp4
  ret i32 0
}
%class.k = type { i32, i32 }
define i32 @__TestMethod_k(%class.k * %this) {
  %tmp5 = alloca i32
  %tmp6 = alloca %class.k
  %tmp7 = call i8 *  @malloc(i32 8)
  %tmp8 = bitcast i8 * %tmp7 to %class.good *
  store %class.good * %tmp8, %class.k * %tmp6
  store i32 10, i32 * %tmp5
  %tmp9 = getelementptr %class.k * %this, i32 0, i32 0
  %tmp10 = load i32 * %tmp9
  ret i32 %tmp10
}
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
