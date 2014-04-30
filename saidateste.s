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
define i32 * @__TestMethod_k(%class.k * %this) {
  %tmp4 = alloca i32 *
  %tmp5 = add i32 * 5, 1
  %tmp7 = mul i32 4, %tmp5
  %tmp8 = call i8* @malloc ( i32 %tmp7)
  %tmp6 = bitcast i8* %tmp8 to i32*
  store i32 5, i32 * %tmp6
  store i32 * %tmp6, i32 * * %tmp4
  %tmp9 = load i32 * * %tmp4
  ret i32 * %tmp9
declare i32 @printf (i8 *, ...)
declare i8 * @malloc (i32)
