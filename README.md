# Android-StyleGan-Kotlin

=============================================

StyleGan Model Reference: https://github.com/genforce/genforce

stylegan_ffhq1024.pth => https://mycuhk-my.sharepoint.com/:u:/g/personal/1155082926_link_cuhk_edu_hk/EdfMxgb0hU9BoXwiR3dqYDEBowCSEF1IcsW3n4kwfoZ9OQ?e=VwIV58&download=1

Model Converting Way: torch.jit.trace

Android App Language: Kotlin



=============================================

This android app generates face images(1024*1024pixels) and its time is about 3000ms in my enviroment.
<img alt="image" src="https://github.com/Castlearchi/Android-StyleGan-Kotlin/blob/master/example_image.png" width="30%" >

=============================================

How to


(1) Create Assets folder "Android-StyleGan-Kotlin/AndroidApp/app/src/main/assets/".

(2) pretrainedModel_to_torchscript.py.

(3) Open AndroidApp And Build, Run.

