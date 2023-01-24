# Android-StyleGan-Kotlin

=============================================

StyleGan Model Reference: https://github.com/genforce/genforce
stylegan_ffhq1024.pth => https://mycuhk-my.sharepoint.com/:u:/g/personal/1155082926_link_cuhk_edu_hk/EdfMxgb0hU9BoXwiR3dqYDEBowCSEF1IcsW3n4kwfoZ9OQ?e=VwIV58&download=1

Model Converting Way: torch.jit.trace

Android App Language: Kotlin

=============================================

This android app generates face images(1024*1024pixels).

Warning: This app is not used coroutines. So When model initialze and compute, maybe this app stop.
