# NSGA_2
多目的進化アルゴリズム(MOEA)の１つNSGA-ⅡのJava実装．交叉手法としてrex-jggリポジトリにあるREXを利用している．（そのため，クラスパスにrex-jggを通している．）


## src/individual
REXにはTIndividualクラスを渡す必要があるが，多目的に対応するために継承したTMOIndividualクラスを実装している．

## src/main
NSGA-Ⅱを実際に利用したmainコード

## src/nsga_2
NSGA-Ⅱが実装されているパッケージ

## problem
問題クラス．今回は2目的に限定されている．(そのため，コードもハードコーディングされている)
