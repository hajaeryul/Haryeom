import { RefObject, useEffect, useState } from 'react';
import { DocumentCallback, PageCallback } from 'react-pdf/dist/cjs/shared/types';

export interface IPdfSize {
    width: number | undefined;
    height: number | undefined;
}

interface IUsePdf {
    initialSelectedPageNumer?: number;
}

const usePdf = ({ initialSelectedPageNumer = 1 }: IUsePdf) => {
    const [totalPagesOfPdfFile, setTotalPagesOfPdfFile] = useState<number>(0);
    const [selectedPageNumber, setSelectedPageNum] = useState<number>(initialSelectedPageNumer);
    const [pdfPageCurrentSize, setPdfPageCurrentSize] = useState<IPdfSize>({
        width: undefined,
        height: undefined,
    });
    const [pdfPageOriginalSize, setPdfPageOriginalSize] = useState<IPdfSize>();
    const [resizeTimer, setResizeTimer] = useState<NodeJS.Timeout>();

    const onDocumentLoadSuccess = (pdfDocument: DocumentCallback) => {
        const { numPages } = pdfDocument;
        setTotalPagesOfPdfFile(numPages);
    };

    const onPageLoadSuccess = (pdfPage: PageCallback) => {
        const { originalWidth, originalHeight } = pdfPage;
        setPdfPageOriginalSize({
            width: originalWidth,
            height: originalHeight,
        });
    };

    const movePage = (selectedPageNumber: number) => {
        setSelectedPageNum(selectedPageNumber);
    };

    const updatePdfPageCurrentSize = (size: IPdfSize) => {
        clearTimeout(resizeTimer);
        const timerId = setTimeout(() => {
            const { width, height } = size;
            setPdfPageCurrentSize({ width, height });
        });
        setResizeTimer(timerId);
    };

    const ZoomOutPdfPageCurrentSize = () => {
        clearTimeout(resizeTimer);
        const timerId = setTimeout(() => {
            if (!pdfPageCurrentSize.width || !pdfPageCurrentSize.height) return;
            setPdfPageCurrentSize({
                width: pdfPageCurrentSize.width * 0.9,
                height: pdfPageCurrentSize.height * 0.9,
            });
        }, 200);
        setResizeTimer(timerId);
    };

    const ZoomInPdfPageCurrentSize = () => {
        clearTimeout(resizeTimer);
        const timerId = setTimeout(() => {
            if (!pdfPageCurrentSize.width || !pdfPageCurrentSize.height) return;
            setPdfPageCurrentSize({
                width: pdfPageCurrentSize.width * 1.1,
                height: pdfPageCurrentSize.height * 1.1,
            });
        }, 200);
        setResizeTimer(timerId);
    };

    return {
        totalPagesOfPdfFile,
        selectedPageNumber,
        pdfPageCurrentSize,
        pdfPageOriginalSize,
        onDocumentLoadSuccess,
        onPageLoadSuccess,
        movePage,
        updatePdfPageCurrentSize,
        ZoomInPdfPageCurrentSize,
        ZoomOutPdfPageCurrentSize,
    };
};

export default usePdf;
