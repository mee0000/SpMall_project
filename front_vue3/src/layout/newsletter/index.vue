<template>
 <section class="newsletter-area section-padding-100-0">
        <div class="container">
            <div class="row align-items-center">
                <!-- Newsletter Text -->
                <div class="col-12 col-lg-6 col-xl-7">
                    <div class="newsletter-text mb-100">
                        <h2>Subscribe for a <span>25% Discount</span></h2>
                        <p>Nulla ac convallis lorem, eget euismod nisl. Donec in libero sit amet mi vulputate consectetur. Donec auctor interdum purus, ac finibus massa bibendum nec.</p>
                    </div>
                </div>
                <!-- Newsletter Form -->
                <div class="col-12 col-lg-6 col-xl-5">
                    <div class="newsletter-form mb-100">
                        <form action="#" method="post">
                            <input type="email" name="email" class="nl-email" placeholder="Your E-mail">
                            <input type="submit" value="Subscribe">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
/* :: 6.0 Newsletter Area CSS */

.newsletter-area {
    position: relative;
    z-index: 1;
    background-color: #3c3c3c;

    .newsletter-form {
        form {
            position: relative;
            z-index: 1;
            input[type="email"] {
                width: 100%;
                height: 50px;
                background-color: #fff;
                color: #afafaf;
                font-size: 14px;
                font-style: italic;
                border: none;
                padding: 0 30px;
            }
            input[type="submit"] {
                @include trans-duration(500ms);
                height: 50px;
                background-color: $bg-default;
                color: #fff;
                font-size: 14px;
                padding: 0 30px;
                cursor: pointer;
                position: absolute;
                top: 0;
                right: 0;
                z-index: 10;
                border: none;
                &:hover,
                &:focus {
                    background-color: $bg-default;
                    color: #fff;
                }
            }
        }
    }
}

</style>